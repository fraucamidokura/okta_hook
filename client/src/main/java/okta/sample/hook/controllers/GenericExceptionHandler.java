package okta.sample.hook.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpServerErrorException;

@ControllerAdvice
public class GenericExceptionHandler {

  @ExceptionHandler(HttpServerErrorException.class)
  public ResponseEntity<ProblemDetail> restTemplateError(HttpServerErrorException error){
    ProblemDetail detail = ProblemDetail.forStatusAndDetail(
        error.getStatusCode(), error.getResponseBodyAsString());
    return  ResponseEntity.of(detail).build();
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ProblemDetail> genericException(Exception error){
    ProblemDetail detail = ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR,
        error.getMessage());
    return ResponseEntity.of(detail).build();
  }
}
