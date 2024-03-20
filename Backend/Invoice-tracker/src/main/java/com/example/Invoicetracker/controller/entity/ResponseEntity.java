package com.example.Invoicetracker.controller.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Arrays;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseEntity<T> {

    private List<ErrorEntity> errors;

    private T data;

    public ResponseEntity(ErrorEntity... errors){
        this.errors = Arrays.asList(errors);
    }

    public ResponseEntity(T response) {
        this.data = response;
    }

}
