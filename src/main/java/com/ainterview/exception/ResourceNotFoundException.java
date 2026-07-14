package com.ainterview.exception;

public class ResourceNotFoundException extends RuntimeException{
    public ResourceNotFoundException(String resource, Long id){
        super(resource + " not found with id: " + id);
    }

    public ResourceNotFoundException(String resource, String fieldName, String fieldValue){
        super(resource + " not found with " + fieldName + ": " + fieldValue);
    }
}
