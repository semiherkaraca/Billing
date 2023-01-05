package com.billing.service;

import com.billing.dto.InvoiceDto;
import com.billing.model.Invoice;
import com.billing.model.User;
import com.billing.request.InvoiceRequest;
import com.billing.request.MultipleInvoiceRequest;

import java.util.List;

public interface InvoiceService {

    Invoice getInvoiceByBillNo(String billNo);

    List<Invoice> getInvoiceList(Long userId);

    InvoiceDto saveInvoice(InvoiceRequest invoiceRequest);

    List<String> saveInvoice(MultipleInvoiceRequest multipleInvoiceRequest);

    String getReport();
}
