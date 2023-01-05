package com.billing.controller;

import com.billing.constants.RequestMapConstants;
import com.billing.dto.InvoiceDto;
import com.billing.model.Invoice;
import com.billing.request.InvoiceRequest;
import com.billing.request.MultipleInvoiceRequest;
import com.billing.service.InvoiceService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(RequestMapConstants.INVOICE_PATH)
@RequiredArgsConstructor
public class InvoiceController {

    private final InvoiceService invoiceService;

    @PostMapping
    @ApiOperation(value = "Save New Invoice")
    public ResponseEntity<InvoiceDto> saveInvoice(@RequestBody InvoiceRequest invoiceRequest) {
        return ResponseEntity.ok(invoiceService.saveInvoice(invoiceRequest));
    }

    @PostMapping("/saveMultiple")
    @ApiOperation(value = "Save Multiple Invoices")
    public ResponseEntity<List<String>> saveInvoice(@RequestBody MultipleInvoiceRequest multipleInvoiceRequest) {
        return ResponseEntity.ok(invoiceService.saveInvoice(multipleInvoiceRequest));
    }

    @GetMapping("/reports")
    @ApiOperation(value = "Get All Invoices Reports")
    public ResponseEntity<String> getReport() {
        return ResponseEntity.ok(invoiceService.getReport());
    }

    @GetMapping("/reports/{billNo}")
    @ApiOperation(value = "Get Invoice By Bill Number")
    public ResponseEntity<Invoice> getInvoiceByBillNo(@PathVariable(name = "billNo") String billNo) {
        return ResponseEntity.ok(invoiceService.getInvoiceByBillNo(billNo));
    }

    @GetMapping("/reports/{userId}")
    @ApiOperation(value = "Get Invoice List By User Id")
    public ResponseEntity<List<Invoice>> getInvoiceList(@PathVariable(name = "userId") Long userId) {
        return ResponseEntity.ok(invoiceService.getInvoiceList(userId));
    }
}
