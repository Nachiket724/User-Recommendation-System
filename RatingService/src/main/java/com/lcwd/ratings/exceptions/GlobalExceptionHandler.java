package com.lcwd.ratings.exceptions;

import com.lcwd.ratings.utils.Constants;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handlerResourceNotFoundException(ResourceNotFoundException ex){
        Map<String, Object> hashMap = new HashMap<>();
        hashMap.put("ERROR: ", ex.getMessage());
        hashMap.put("SUCCESS: ", false);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(hashMap);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handlerGeneralException(Exception ex){
        StackTraceElement[] stackTraceElements = ex.getStackTrace();
        String errorLocation = stackTraceElements.length>0 ? stackTraceElements[0].toString() : Constants.UNKNOWN_ERROR;
        System.out.println("ERROR LOCATION: "+errorLocation);
        System.out.println("ERROR: "+ex.getMessage());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
    }
}
