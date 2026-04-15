package aho.response;

import java.util.List;

@lombok.Data
public class ResponseUser {
    private boolean result;
    private List<CommonData> userDataList;
}
