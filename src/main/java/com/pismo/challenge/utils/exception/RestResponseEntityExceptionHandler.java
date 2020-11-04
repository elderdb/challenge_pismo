package com.pismo.challenge.utils.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
@Component
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

  final Logger log = LoggerFactory.getLogger(RestResponseEntityExceptionHandler.class);

  @ExceptionHandler(BadRequestException.class)
  private ResponseEntity<?> handleBadRequestException(
      final RuntimeException ex, final WebRequest request) {

    log.warn(ex.getMessage());

    return ResponseEntity.badRequest().body(ex.getMessage());

  }

  @Override
  public final ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                   HttpHeaders headers, HttpStatus status,
                                                                   WebRequest request) {

    final List<HashMap<String, String>> details = ex.getBindingResult()
        .getAllErrors()
        .parallelStream()
        .map(objectError -> {

          final HashMap<String, String> error = new HashMap<>();

          error.put("field", ((FieldError) objectError).getField());
          error.put("message", objectError.getDefaultMessage());

          return error;

        })
        .collect(Collectors.toList());

    return ResponseEntity.badRequest().body(details);
  }

}
