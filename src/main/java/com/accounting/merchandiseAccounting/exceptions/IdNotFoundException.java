package com.accounting.merchandiseAccounting.exceptions;


import javax.persistence.NoResultException;

public class IdNotFoundException extends NoResultException {
    public IdNotFoundException(String message) {
        super(message);
    }
}
