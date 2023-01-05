package com.billing.service.impl;

import com.billing.config.ApplicationProperties;
import com.billing.dto.InvoiceDto;
import com.billing.dto.ProductDto;
import com.billing.enums.Currency;
import com.billing.enums.InvoiceStatus;
import com.billing.exception.BadRequestException;
import com.billing.model.Invoice;
import com.billing.model.Product;
import com.billing.model.User;
import com.billing.repository.InvoiceRepository;
import com.billing.request.InvoiceRequest;
import com.billing.service.ProductService;
import com.billing.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class InvoiceServiceImplTest {

    @InjectMocks
    private InvoiceServiceImpl invoiceService;
    @Mock
    private ApplicationProperties applicationProperties;
    @Mock
    private InvoiceRepository invoiceRepository;
    @Mock
    private UserService userService;
    @Mock
    private ProductService productService;

    @Test
    public void shouldReturnInvoiceByBillNo() {
        //given
        String billNumber = "TR001";
        Product product1 = Product.builder().id(1L).name("Product1").build();
        Invoice invoice = Invoice.builder()
                .billNo(billNumber)
                .amount(new BigDecimal(20))
                .currency(Currency.TL)
                .description("Invoice1 Description")
                .status(InvoiceStatus.APPROVED)
                .product(product1)
                .build();
        //when
        when(invoiceRepository.findByBillNo(billNumber)).thenReturn(Optional.of(invoice));
        //then
        Invoice resultInvoice = invoiceService.getInvoiceByBillNo(billNumber);
        assertEquals(invoice.getBillNo(), resultInvoice.getBillNo());
    }

    @Test
    public void shouldThrowExceptionWhenInvoiceExists() {
        //given
        Long userId = 1L;
        String dummyEmail = "dummy@gmail.com";
        String billNumber = "TR001";
        Product product1 = Product.builder().id(1L).name("Product1").build();
        Product product2 = Product.builder().id(2L).name("Product2").build();
        Invoice invoice = Invoice.builder()
                .billNo(billNumber)
                .amount(new BigDecimal(20))
                .currency(Currency.TL)
                .description("Invoice1 Description")
                .status(InvoiceStatus.APPROVED)
                .product(product1)
                .build();
        User user = User.builder().firstname("Semih").lastname("Erkaraca")
                .products(Arrays.asList(product1, product2))
                .email(dummyEmail).build();
        //when
        when(userService.findByUserId(userId)).thenReturn(user);
        when(productService.getUserProducts(user.getEmail())).thenReturn(Arrays.asList(ProductDto.of(product1), ProductDto.of(product2)));
        when(invoiceRepository.findByProduct(Arrays.asList(product1.getId(), product2.getId()))).thenReturn(Arrays.asList(invoice));
        //then
        List<Invoice> invoiceList = invoiceService.getInvoiceList(userId);
        assertEquals(invoice.getBillNo(), invoiceList.get(0).getBillNo());
    }

    @Test
    public void shouldThrowExceptionWhenBillExists() {
        String email = "dummy@gmail.com";
        InvoiceRequest invoiceRequest = new InvoiceRequest("Semih","Erkaraca",email,new BigDecimal(100),"Product1","TR000");
        Product product1 = Product.builder().id(2L).name("Product2").build();
        Invoice invoice = Invoice.builder()
                .billNo(invoiceRequest.getBillNo())
                .amount(invoiceRequest.getAmount())
                .currency(Currency.TL)
                .description("Invoice1 Description")
                .status(InvoiceStatus.APPROVED)
                .product(product1)
                .build();
        when(invoiceRepository.findByBillNo(invoiceRequest.getBillNo())).thenReturn(Optional.of(invoice));
        try {
            invoiceService.saveInvoice(invoiceRequest);
        } catch (Exception e) {
            //then
            assertTrue(e instanceof BadRequestException);
            assertEquals("Invoice created before with BillNo: " + invoiceRequest.getBillNo(), e.getMessage());
        }
    }

    @Test
    public void shouldSaveInvoice() {
        String email = "dummy@gmail.com";
        InvoiceRequest invoiceRequest = new InvoiceRequest(
                "Semih",
                "Erkaraca",
                email,
                new BigDecimal(100),
                "Product1",
                "TR000");
        User user = User.builder().firstname("Semih").lastname("Erkaraca")
                .email(email).build();

        Product product1 = Product.builder().id(1L).user(user).name("Product1").build();
        Product product2 = Product.builder().id(2L).user(user).name("Product2").build();
        Invoice invoice = Invoice.builder()
                .billNo(invoiceRequest.getBillNo())
                .amount(invoiceRequest.getAmount())
                .currency(Currency.TL)
                .description("Invoice1 Description")
                .status(InvoiceStatus.APPROVED)
                .product(product1)
                .build();

        List<Invoice> invoiceList = Arrays.asList(invoice);
        //when
        when(invoiceRepository.findByBillNo(invoiceRequest.getBillNo())).thenReturn(Optional.empty());
        when(userService.findByEmail(invoiceRequest.getUserEmail())).thenReturn(user);
        when(productService.getProductIds(user.getId())).thenReturn(Arrays.asList(product1.getId(), product2.getId()));
        when(invoiceRepository.findByProduct(any())).thenReturn(invoiceList);
        when(productService.findProductByUserAndName(user, invoiceRequest.getProductName())).thenReturn(product1);
        when(applicationProperties.getInvoiceLimit()).thenReturn(new BigDecimal(200));
        when(invoiceRepository.save(invoice)).thenReturn(invoice);

        InvoiceDto invoiceDto = invoiceService.saveInvoice(invoiceRequest);

        assertEquals(InvoiceStatus.APPROVED, invoiceDto.getInvoiceStatus());
    }

    @Test
    public void shouldReturnReport() {
        String email = "dummy@gmail.com";
        InvoiceRequest invoiceRequest1 = new InvoiceRequest("Semih", "Erkaraca", email, new BigDecimal(100), "Product1", "TR000");
        InvoiceRequest invoiceRequest2 = new InvoiceRequest("Semih", "Erkaraca", email, new BigDecimal(130), "Product2", "TR001");
        User user = User.builder().firstname("Semih").lastname("Erkaraca").email(email).build();

        Product product1 = Product.builder().id(1L).user(user).name("Product1").build();
        Product product2 = Product.builder().id(2L).user(user).name("Product2").build();
        Invoice invoice1 = Invoice.builder()
                .billNo(invoiceRequest1.getBillNo())
                .amount(invoiceRequest1.getAmount())
                .currency(Currency.TL)
                .description("Invoice1 Description")
                .product(product1)
                .status(InvoiceStatus.APPROVED)
                .build();

        Invoice invoice2 = Invoice.builder()
                .billNo(invoiceRequest2.getBillNo())
                .amount(invoiceRequest2.getAmount())
                .currency(Currency.TL)
                .description("Invoice1 Description")
                .product(product2)
                .status(InvoiceStatus.REJECTED)
                .build();

        List<Invoice> invoiceList = Arrays.asList(invoice1, invoice2);
        //when
        when(invoiceRepository.findAll()).thenReturn(invoiceList);

        String report = invoiceService.getReport();
        assertEquals("2 fatura girişi yapıldı. 1 fatura reddedildi.", report);
    }

}