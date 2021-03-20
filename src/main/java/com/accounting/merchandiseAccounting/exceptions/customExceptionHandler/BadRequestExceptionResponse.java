package com.accounting.merchandiseAccounting.exceptions.customExceptionHandler;

public class BadRequestExceptionResponse {

    private String projectIdentifier;

    public BadRequestExceptionResponse(String projectIdentifier) {
        this.projectIdentifier = projectIdentifier;
    }

    public String getProjectIdentifier() {
        return projectIdentifier;
    }

    public void setProjectIdentifier(String projectIdentifier) {
        this.projectIdentifier = projectIdentifier;
    }
}
