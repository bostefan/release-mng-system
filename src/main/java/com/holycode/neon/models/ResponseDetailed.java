package com.holycode.neon.models;

import java.util.Map;

public class ResponseDetailed extends Response{

    private Map<String, String> details;

    public ResponseDetailed() {
    }

    public ResponseDetailed(String status, String message, Map<String, String> details) {
        super(status,message);
        this.details = details;
    }

    public Map<String, String> getDetails() {
        return details;
    }

    public void setDetails(Map<String, String> details) {
        this.details = details;
    }
}
