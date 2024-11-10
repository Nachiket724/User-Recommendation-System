package com.lcwd.ratings.exceptions;

public class ResourceNotFoundException extends RuntimeException{

    ResourceNotFoundException(){
        super();
    }

    ResourceNotFoundException(String errorMessage){
        super(errorMessage);
    }
}
