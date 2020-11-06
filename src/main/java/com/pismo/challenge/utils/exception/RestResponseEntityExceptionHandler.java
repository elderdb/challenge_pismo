package com.pismo.challenge.utils.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.pismo.challenge.enums.ErrorMessageEnum.INPUT_OBJECT_ERROR_PARSE;

@ControllerAdvice
@Component
public class RestResponseEntityExceptionHandler {

  final Logger log = LoggerFactory.getLogger(RestResponseEntityExceptionHandler.class);

  @ExceptionHandler(BadRequestException.class)
  private ResponseEntity<?> handleBadRequestException(
      final RuntimeException ex, final WebRequest request) {

    log.warn(ex.getMessage());

    return ResponseEntity.badRequest().body(ex.getMessage());

  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(code = HttpStatus.BAD_REQUEST)
  public final List<HashMap<String, String>> handleMethodArgumentNotValid(final MethodArgumentNotValidException ex) {

    return ex.getBindingResult()
        .getAllErrors()
        .parallelStream()
        .map(objectError -> {

          final HashMap<String, String> error = new HashMap<>();

          error.put("field", ((FieldError) objectError).getField());
          error.put("message", objectError.getDefaultMessage());

          return error;

        })
        .collect(Collectors.toList());

  }

  @ExceptionHandler(HttpMessageNotReadableException.class)
  @ResponseStatus(code = HttpStatus.BAD_REQUEST)
  @ResponseBody
  public final Map handleHttpMessageNotReadable() {

    final HashMap<String, String> map = new HashMap<>();

    map.put("message", INPUT_OBJECT_ERROR_PARSE.getMessage());

    return map;

  }

}
