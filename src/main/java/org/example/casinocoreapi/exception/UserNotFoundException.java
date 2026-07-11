package org.example.casinocoreapi.exception;

public class UserNotFoundException  extends RuntimeException {

    public UserNotFoundException(String message){
        super(message);
    }

}
