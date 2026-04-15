package aho.response;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
@Data
public class ResponseStatus {
    private boolean result;
    private List<CommonData> listStatus = new ArrayList<>();
}
