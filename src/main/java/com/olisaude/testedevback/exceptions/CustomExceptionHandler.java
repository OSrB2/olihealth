package com.olisaude.testedevback.exceptions;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler{

  @org.springframework.web.bind.annotation.ExceptionHandler(HandleIDNotFound.class)
  public ResponseEntity<Object> handleIdNotFound(HandleIDNotFound e) {
    Map<String, Object> body = new LinkedHashMap<>();
    body.put("message", e.getMessage());

    return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
  }
  

  @org.springframework.web.bind.annotation.ExceptionHandler(HandleNoHasClients.class)
  public ResponseEntity<Object> handleNoHasClients(HandleNoHasClients e) {
    Map<String, Object> body = new LinkedHashMap<>();
    body.put("message", e.getMessage());

    return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
  }

  @org.springframework.web.bind.annotation.ExceptionHandler(HandleValidationField.class)
  public ResponseEntity<Object> handleValidationField(HandleValidationField e) {
    Map<String, Object> body = new LinkedHashMap<>();
    body.put("message", e.getMessage());
    
    return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
  }
}
