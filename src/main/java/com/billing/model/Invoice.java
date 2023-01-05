package com.billing.model;

import com.billing.enums.Currency;
import com.billing.enums.InvoiceStatus;
import com.billing.request.InvoiceRequest;
import com.billing.util.DateUtils;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.math.BigDecimal;

@Getter
@SuperBuilder
@EqualsAndHashCode(callSuper = false, of = {"id"})
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "INVOICE")
public class Invoice extends AbstractEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "BILL_NO", unique = true)
    private String billNo;

    @Column(name = "AMOUNT")
    private BigDecimal amount;

    @Column(name = "CURRENCY")
    @Enumerated(EnumType.STRING)
    private Currency currency;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "STATUS")
    @Enumerated(EnumType.STRING)
    private InvoiceStatus status;

    @ManyToOne
    @JoinColumn(name = "PRODUCT_ID")
    private Product product;

    @Override
    public Long getId() {
        return this.id;
    }

    public void approveInvoice() {
        this.status = InvoiceStatus.APPROVED;
    }

    public static Invoice generateInvoice(InvoiceRequest invoiceRequest, User user, Product product, InvoiceStatus invoiceStatus) {
        return Invoice.builder()
                .billNo(invoiceRequest.getBillNo())
                .amount(invoiceRequest.getAmount())
                .currency(Currency.TL)
                .description(user.getEmail() + " Kullanıcısının " + product.getName() + " Ürünün Faturası")
                .status(invoiceStatus)
                .product(product)
                .createdDate(DateUtils.getSimpleCurrentDate())
                .createdBy(user.getEmail())
                .build();
    }
}
