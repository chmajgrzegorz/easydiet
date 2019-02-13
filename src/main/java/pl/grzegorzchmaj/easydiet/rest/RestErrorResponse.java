package pl.grzegorzchmaj.easydiet.rest;

import lombok.Data;

@Data
class RestErrorResponse {

    private int httpStatus;
    private String message;
    private long timeStamp;
}
