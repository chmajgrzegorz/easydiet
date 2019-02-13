package pl.grzegorzchmaj.easydiet.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<RestErrorResponse> handleException(Exception exc){

        RestErrorResponse restErrorResponse = new RestErrorResponse();

        restErrorResponse.setHttpStatus(HttpStatus.BAD_REQUEST.value());
        restErrorResponse.setMessage(exc.getMessage());
        restErrorResponse.setTimeStamp(System.currentTimeMillis());


        return new ResponseEntity<>(restErrorResponse, HttpStatus.BAD_REQUEST);
    }

}
