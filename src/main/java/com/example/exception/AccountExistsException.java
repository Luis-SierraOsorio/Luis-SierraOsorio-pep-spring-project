package com.example.exception;

public class AccountExistsException extends Exception{
    public AccountExistsException(String message){
        super(message);
    }
}
