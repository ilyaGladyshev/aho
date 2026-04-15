package aho.response;

import lombok.Data;

import java.util.List;
@Data
public class ResponseArea {
    private boolean result;
    private List<CommonData> listArea;

    public static class ResponseWay {
    }
}
