package com.accounting.merchandiseAccounting.exceptions.customExceptionHandler;

public class IdNotFoundExceptionResponse {

    private String projectIdentifier;

    public IdNotFoundExceptionResponse(String projectIdentifier) {
        this.projectIdentifier = projectIdentifier;
    }

    public String getProjectIdentifier() {
        return projectIdentifier;
    }

    public void setProjectIdentifier(String projectIdentifier) {
        this.projectIdentifier = projectIdentifier;
    }
}
