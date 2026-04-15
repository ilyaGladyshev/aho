package aho.service;

import aho.response.*;
import aho.response.form.FormAddRequest;
import aho.response.form.FormEquipment;
import aho.response.form.FormFiles;
import org.springframework.web.multipart.MultipartFile;

public interface RequestAddService {
    ResponseUser getUsersRequest();
    ResponseUser getUsersStatus();
    ResponseEquipment getEquipmentL1();
    ResponseEquipment getEquipmentL2(String category);
    ResponseArea getArea();
    ResponseDate getCurrentDate();
    ResponseStatus getStatus();
    ResponseSettings getWayStorage();
    ResponseJournal writeRequest(FormAddRequest formAddRequest);
    ResponseCommon writeFiles(String requestId, MultipartFile[] files);
    ResponseCommon addEquipment(FormEquipment formEquipment);
}
