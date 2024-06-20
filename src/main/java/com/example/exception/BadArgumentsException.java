package com.example.exception;

public class BadArgumentsException extends Exception{
    public BadArgumentsException(String message){
       super(message);
    }
}
