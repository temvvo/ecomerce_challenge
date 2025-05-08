package com.ecommerce.challenge.infrastructure.adapter.api.config;


import com.ecommerce.challenge.domain.exception.EcommerceException;
import com.ecommerce.challenge.infrastructure.adapter.api.error.ErrorMessage;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import java.io.IOException;

@Slf4j
@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(EcommerceException.class)
  public ResponseEntity<ErrorMessage> handleBadRequest(EcommerceException ex, HttpServletResponse response) throws IOException {

    ErrorMessage body = new ErrorMessage();

    body.setCode(String.valueOf(ex.getStatus()));
    body.setMessage(ex.getMessage());
    log.error("RestExceptionHandler ApiExceptionHandler. ", ex);
    if(ex.getStatus()>= 500) {
      return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    return new ResponseEntity<>(body,HttpStatus.BAD_REQUEST);
  }
}
