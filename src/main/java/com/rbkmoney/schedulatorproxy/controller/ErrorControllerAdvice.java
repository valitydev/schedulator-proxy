package com.rbkmoney.schedulatorproxy.controller;

import com.rbkmoney.schedulatorproxy.exception.ScheduleJobException;
import com.rbkmoney.schedulatorproxy.model.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@Slf4j
public class ErrorControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ScheduleJobException.class)
    public ResponseEntity<ErrorResponse> handleUsersNotProvided(ScheduleJobException e) {
        log.error(e.getMessage());
        ErrorResponse errorResponse = ErrorResponse.builder()
              .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
              .message(e.getMessage())
              .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
