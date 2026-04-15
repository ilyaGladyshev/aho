package aho.response;

import lombok.Data;

import java.util.List;
@Data
public class ResponseSettings {
    private boolean result;
    private List<CommonData> listSettings;
}
