package com.lcwd.hotel.exceptions;

import com.lcwd.hotel.payload.ApiResponse;
import com.lcwd.hotel.utils.Constants;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse> handlerResourceNotFoundException(ResourceNotFoundException ex){
        ApiResponse apiResponse = ApiResponse.builder().message(ex.getMessage()).success(false).httpStatus(HttpStatus.NOT_FOUND).build();
        return new ResponseEntity<>(apiResponse,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<String>> handlerMethodNotValidException(MethodArgumentNotValidException ex){
        List<String> errors = ex.getBindingResult().getFieldErrors().stream().map(error -> error.getField()+": "+error.getDefaultMessage()).toList();
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handlerGeneralException(Exception ex){
        StackTraceElement[] stackTraceElements = ex.getStackTrace();
        String errorLocation = stackTraceElements.length > 0 ? stackTraceElements[0].toString() : Constants.UNKNOWN_ERROR;
        System.out.println("ERROR LOCATION: "+errorLocation);
        System.out.println("ERROR: "+ex.getMessage());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("AN ERROR OCCURED: "+ex.getMessage());
    }
}
