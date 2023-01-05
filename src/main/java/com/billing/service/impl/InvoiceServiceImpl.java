package com.billing.service.impl;

import com.billing.config.ApplicationProperties;
import com.billing.dto.InvoiceDto;
import com.billing.dto.ProductDto;
import com.billing.dto.UserDto;
import com.billing.enums.InvoiceStatus;
import com.billing.exception.BadRequestException;
import com.billing.exception.NotFoundException;
import com.billing.model.Invoice;
import com.billing.model.Product;
import com.billing.model.User;
import com.billing.repository.InvoiceRepository;
import com.billing.request.InvoiceRequest;
import com.billing.request.MultipleInvoiceRequest;
import com.billing.service.InvoiceService;
import com.billing.service.ProductService;
import com.billing.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InvoiceServiceImpl implements InvoiceService {

    private final ApplicationProperties applicationProperties;
    private final InvoiceRepository invoiceRepository;
    private final UserService userService;
    private final ProductService productService;

    @Override
    public Invoice getInvoiceByBillNo(String billNo) {
        Optional<Invoice> optionalInvoice = invoiceRepository.findByBillNo(billNo);
        return optionalInvoice.isPresent() ? optionalInvoice.get() : null;
    }

    @Override
    public List<Invoice> getInvoiceList(Long userId) {
        List<ProductDto> userProducts = productService.getUserProducts(userService.findByUserId(userId).getEmail());
        return invoiceRepository.findByProduct(userProducts.stream()
                .map(productDto -> productDto.getProductId())
                .distinct()
                .collect(Collectors.toList()));
    }

    @Override
    @Transactional
    public InvoiceDto saveInvoice(InvoiceRequest invoiceRequest) {
        Optional<Invoice> optionalInvoice = invoiceRepository.findByBillNo(invoiceRequest.getBillNo());
        if (optionalInvoice.isPresent()) {
            throw new BadRequestException("Invoice created before with BillNo: " + invoiceRequest.getBillNo());
        }
        User user = userService.findByEmail(invoiceRequest.getUserEmail());
        List<Invoice> userInvoiceList = invoiceRepository.findByProduct(productService.getProductIds(user.getId()));

        BigDecimal userTotalBillsAmount = userInvoiceList.stream().filter(invoice -> invoice.getStatus().isApproved())
                .map(invoice -> invoice.getAmount()).reduce(BigDecimal.ZERO, BigDecimal::add);
        InvoiceStatus invoiceStatus = InvoiceStatus.REJECTED;
        if (applicationProperties.getInvoiceLimit().compareTo(userTotalBillsAmount.add(invoiceRequest.getAmount())) >= 0) {
            invoiceStatus = InvoiceStatus.APPROVED;
        }
        Product product = productService.findProductByUserAndName(user, invoiceRequest.getProductName());
        Invoice invoice = Invoice.generateInvoice(invoiceRequest, user, product, invoiceStatus);
        return InvoiceDto.of(invoiceRepository.save(invoice));
    }

    @Override
    @Transactional
    public List<String> saveInvoice(MultipleInvoiceRequest multipleInvoiceRequest) {
        List<String> result = new ArrayList<>();
        int i = 1;
        for (InvoiceRequest invoiceRequest : multipleInvoiceRequest.getInvoiceRequestList()) {
            InvoiceDto invoiceDto = saveInvoice(invoiceRequest);
            if (invoiceDto.getInvoiceStatus().isRejected()) {
                UserDto user = userService.getUser(invoiceRequest.getUserEmail());
                if (multipleInvoiceRequest.getInvoiceRequestList().size() == 1) {
                    result.add(user.getName() + "'nin işemi reddedilir.");
                } else {
                    result.add(user.getName() + "'nin " + i + ". işlemi reddedilir.");
                }
            }
            i++;
        }
        return result;
    }

    @Override
    public String getReport() {
        List<Invoice> invoiceList = invoiceRepository.findAll();
        int processCount = invoiceList.size();
        long rejectedProcessCount = invoiceList.stream().filter(invoice -> invoice.getStatus().isRejected()).count();
        return processCount + " fatura giri\u015Fi yap\u0131ld\u0131. " + rejectedProcessCount + " fatura reddedildi.";
    }
}
