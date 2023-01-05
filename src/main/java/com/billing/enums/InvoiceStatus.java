package com.billing.enums;

public enum InvoiceStatus {
    WAITING_APPROVAL,
    APPROVED,
    REJECTED;

    public boolean isRejected() {
        return REJECTED.equals(this);
    }

    public boolean isApproved() {
        return APPROVED.equals(this);
    }
}
