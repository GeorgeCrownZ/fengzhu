package com.soft.web.expressapi.request;

import lombok.Data;


@Data
public class HttpResult {
    private int status;
    private String body;
    private String error;
    private String express;

    public HttpResult() {
    }

    public HttpResult(int status, String body, String error, String express) {
        this.status = status;
        this.body = body;
        this.error = error;
        this.express = express;
    }
}
