package com.example.Invoicetracker.controller;

import com.example.Invoicetracker.controller.entity.ErrorEntity;
import com.example.Invoicetracker.controller.entity.ResponseEntity;
import com.example.Invoicetracker.exception.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.ArrayList;
import java.util.List;


@ControllerAdvice
public class BaseControllerAdvice {

    Logger logger = LoggerFactory.getLogger(BaseControllerAdvice.class);

    @ExceptionHandler(AuthorizationException.class)
    @ResponseStatus(code = HttpStatus.FORBIDDEN)
    @ResponseBody
    public ResponseEntity<String> authorizationException(AuthorizationException exception) {
        logger.error("InvoiceTracker BaseController : " + exception.getMessage());
        return new ResponseEntity<String>(new ErrorEntity(InvoiceTrackerErrorCode.FORBIDDEN, "User have no authorization"));
    }

    @ExceptionHandler(MissingPathVariableException.class)
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    public @ResponseBody
    ResponseEntity<String> missingPathVariableException(MissingPathVariableException exception) {
        logger.error("InvoiceTracker BaseController : " + exception.getVariableName());
        return new
                ResponseEntity<String>(new ErrorEntity(InvoiceTrackerErrorCode.NOT_FOUND, "Path Variable not found:: " + exception.getVariableName()));
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    @ResponseBody
    public ResponseEntity<String> userNotFoundException(UserNotFoundException exception) {
        logger.error("InvoiceTracker BaseController : " + exception.getMessage());
        return new ResponseEntity<String>(new ErrorEntity(InvoiceTrackerErrorCode.NOT_FOUND, "No user found with this id"));
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ResponseEntity<String> argumentMissedException(MissingServletRequestParameterException e) {
        logger.error("InvoiceTracker BaseController : " + e.getMessage(),
                InvoiceTrackerErrorCode.VALIDATION_ERROR.getCode(), e);
        List<ErrorEntity> errorEntities = new ArrayList<>();

        ErrorEntity errorEntity = new ErrorEntity(InvoiceTrackerErrorCode.VALIDATION_ERROR, "Argument is missing");
        errorEntities.add(errorEntity);
        return new ResponseEntity<>(errorEntities.toArray(new ErrorEntity[0]));
    }

    @ExceptionHandler(DuplicateEmailException.class)
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    @ResponseBody
    public ResponseEntity<String> duplicatedEmailException(DuplicateEmailException exception) {
        logger.error("InvoiceTracker BaseController : " + exception.getMessage());
        return new ResponseEntity<String>(new ErrorEntity(InvoiceTrackerErrorCode.CONFLICT, "User already found"));
    }

    @ExceptionHandler(FileNotFoundException.class)
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    @ResponseBody
    public ResponseEntity<String> fileNotFoundException(FileNotFoundException exception) {
        logger.error("InvoiceTracker BaseController : " + exception.getMessage());
        return new ResponseEntity<String>(new ErrorEntity(InvoiceTrackerErrorCode.NOT_FOUND, "File not found"));
    }

    @ExceptionHandler(InvoiceNotFoundException.class)
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    @ResponseBody
    public ResponseEntity<String> invoiceNotFoundException(InvoiceNotFoundException exception) {
        logger.error("InvoiceTracker BaseController : " + exception.getMessage());
        return new ResponseEntity<String>(new ErrorEntity(InvoiceTrackerErrorCode.NOT_FOUND, "Invoice not found"));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ResponseEntity<String> validationException(MethodArgumentNotValidException e) {
        logger.error("InvoiceTracker BaseController : " + e.getMessage());
        List<FieldError> errors = e.getBindingResult().getFieldErrors();
        List<ErrorEntity> errorEntities = new ArrayList<>();

        for (FieldError error : errors) {
            String errorMessage = error.getDefaultMessage();
            ErrorEntity errorEntity = new ErrorEntity(InvoiceTrackerErrorCode.VALIDATION_ERROR, errorMessage, "Method argument not valid");
            errorEntities.add(errorEntity);
        }
        return new ResponseEntity<>(errorEntities.toArray(new ErrorEntity[0]));
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ResponseEntity<String> handleException(Exception ex) {
        logger.error("InvoiceTracker BaseController : " + ex.getMessage());
        return new ResponseEntity<String>(new ErrorEntity(InvoiceTrackerErrorCode.INTERNAL_SERVER_ERROR, "An unexpected error occurred"));
    }

}
