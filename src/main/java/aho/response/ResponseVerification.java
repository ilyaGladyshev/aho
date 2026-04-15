package aho.response;

import lombok.Data;

@Data
public class ResponseVerification {
    private boolean result;
    private String error;
    private String status;
    private String id;
}
