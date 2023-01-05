package com.billing.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceRequest {

    private String firstName;
    private String lastName;
    private String userEmail;
    private BigDecimal amount;
    private String productName;
    private String billNo;
}
