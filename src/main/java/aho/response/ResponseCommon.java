package aho.response;

import lombok.Data;

@Data
public class ResponseCommon {
    private boolean result;
    private String error;
}
