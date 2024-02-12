package com.chatters.ChatApp.Excpetion;

import com.chatters.ChatApp.models.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(UserAlreadyExistException.class)
    public ResponseEntity<ErrorResponse> handleEmailAlreadyExistsException(UserAlreadyExistException exception){

        ErrorResponse errorDetails = new ErrorResponse(
                LocalDateTime.now(),
                exception.getMessage(),
                HttpStatus.BAD_REQUEST.toString()
        );

        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(InvalidPassword.class)
    public ResponseEntity<ErrorResponse> handleInvalidPasswordException(UserAlreadyExistException exception){

        ErrorResponse errorDetails = new ErrorResponse(
                LocalDateTime.now(),
                "Invalid Password",
                HttpStatus.UNAUTHORIZED.toString()
        );

        return new ResponseEntity<>(errorDetails, HttpStatus.UNAUTHORIZED);
    }
    @ExceptionHandler(UserDoesNotExist.class)
    public ResponseEntity<ErrorResponse> handleUserDoesNotExistException(UserAlreadyExistException exception){

        ErrorResponse errorDetails = new ErrorResponse(
                LocalDateTime.now(),
                exception.getMessage(),
                HttpStatus.UNAUTHORIZED.toString()
        );

        return new ResponseEntity<>(errorDetails, HttpStatus.UNAUTHORIZED);
    }
}
