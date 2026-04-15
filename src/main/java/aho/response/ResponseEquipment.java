package aho.response;

import aho.model.Equipment;
import lombok.Data;

import java.util.List;
@Data
public class ResponseEquipment {
    private boolean result;
    private List<CommonData> listEquipment;
}
