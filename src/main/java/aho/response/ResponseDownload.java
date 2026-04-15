package aho.response;

import lombok.Data;

@Data
public class ResponseDownload {
    private Boolean result;
    private byte[] byteFile;
}
