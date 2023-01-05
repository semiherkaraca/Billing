package com.billing.dto;

import com.billing.enums.InvoiceStatus;
import com.billing.model.Invoice;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceDto implements Serializable {

    private static final long serialVersionUID = 6578026379385085864L;
    private Long productId;
    private Long userId;
    private BigDecimal amount;
    private InvoiceStatus invoiceStatus;

    public static InvoiceDto of(Invoice invoice) {
        return InvoiceDto.builder()
                .productId(invoice.getProduct().getId())
                .amount(invoice.getAmount())
                .userId(invoice.getProduct().getUser().getId())
                .invoiceStatus(invoice.getStatus())
                .build();
    }
}
