package com.accounting.merchandiseAccounting.exceptions.customExceptionHandler;

public class CustomInternalErrorResponse {

    private String customInternalError;

    public CustomInternalErrorResponse(String customInternalError) {
        this.customInternalError = customInternalError;
    }

    public String getCustomInternalError() {
        return customInternalError;
    }

    public void setCustomInternalError(String customInternalError) {
        this.customInternalError = customInternalError;
    }
}
