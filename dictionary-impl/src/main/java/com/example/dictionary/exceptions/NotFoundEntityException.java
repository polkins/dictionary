package com.example.dictionary.exceptions;

public class NotFoundEntityException extends Exception{
    public NotFoundEntityException(String errorMessage){
        super(errorMessage);
    }
}
