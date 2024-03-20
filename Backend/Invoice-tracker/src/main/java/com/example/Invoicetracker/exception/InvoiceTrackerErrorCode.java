package com.example.Invoicetracker.exception;

public enum InvoiceTrackerErrorCode {

    INTERNAL_SERVER_ERROR(500),

    FORBIDDEN(403),

    UNAUTHENTICATED(401),

    VALIDATION_ERROR(400),

    CONFLICT(409),

    NOT_FOUND(404);

    private int code;

    private InvoiceTrackerErrorCode(int code){
        setCode(code);
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

}
