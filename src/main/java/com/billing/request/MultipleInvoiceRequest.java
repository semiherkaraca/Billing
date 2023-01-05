package com.billing.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MultipleInvoiceRequest {

    private List<InvoiceRequest> invoiceRequestList;
}
