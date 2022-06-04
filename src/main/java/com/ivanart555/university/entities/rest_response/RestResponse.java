package com.ivanart555.university.entities.rest_response;

import org.springframework.http.HttpStatus;

public class RestResponse {
    private int status = 200;

    private HttpStatus statusMessage = HttpStatus.OK;

    public RestResponse(int status, HttpStatus statusMessage) {
        this.status = status;
        this.statusMessage = statusMessage;
    }

    public RestResponse() {
    }
}
