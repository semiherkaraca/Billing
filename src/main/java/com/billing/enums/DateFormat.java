package com.billing.enums;

public enum DateFormat {
    DATE_FORMAT_DD_MM_YYYY_HH_MM_SS("dd/MM/yyyy HH:mm:ss"),
    DATE_FORMAT_DD_MM_YYYY_HH_MM("dd/MM/yyyy HH:mm"),
    DATE_FORMAT_DD_MM_YYYY_HH("dd/MM/yyyy HH"),
    DATE_FORMAT_DD_MM_YYYY("dd/MM/yyyy");

    private String dateFormat;

    DateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
    }

    public String getDateFormat() {
        return dateFormat;
    }
}
