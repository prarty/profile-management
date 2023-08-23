package com.intuit.sride.profilemanagement.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String userDoesNotExist) {
        super(userDoesNotExist);
    }
}
