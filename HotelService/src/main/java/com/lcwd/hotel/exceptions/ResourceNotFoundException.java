package com.lcwd.hotel.exceptions;

public class ResourceNotFoundException extends RuntimeException{

    public ResourceNotFoundException(){
        super("Resource not found on the server!");
    }

    public ResourceNotFoundException(String errorMsg){
        super(errorMsg);
    }
}
