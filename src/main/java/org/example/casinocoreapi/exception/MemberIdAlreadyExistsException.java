package org.example.casinocoreapi.exception;

public class MemberIdAlreadyExistsException extends RuntimeException {
    public MemberIdAlreadyExistsException(String message) {
        super(message);
    }
}
