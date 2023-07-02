package com.db.loanstorage.exception;

public class InvalidLoanException extends RuntimeException {

    private final String id;

    public InvalidLoanException(final String id) {
        super("Invalid Loan: " + id);
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
