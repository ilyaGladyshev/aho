package aho.response;

import lombok.Data;

import java.util.List;
@Data
public class ResponseEquipment {
    private boolean result;
    private List<CommonData> listEquipment;
}
