package com.billing.constants;

public class RequestMapConstants {

    private RequestMapConstants() {
    }

    public static final String API = "api";
    public static final String VERSION = "v1";
    public static final String SPLIT = "/";

    public static final String API_VERSION = API + SPLIT + VERSION;

    public static final String USER_PATH = API_VERSION + SPLIT + "users";
    public static final String PRODUCT_PATH = API_VERSION + SPLIT + "products";
    public static final String INVOICE_PATH = API_VERSION + SPLIT + "invoices";
}
