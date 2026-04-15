package aho.service.impl;

import aho.model.*;
import aho.repository.*;
import aho.response.*;
import aho.response.form.FormAddRequest;
import aho.response.form.FormEquipment;
import aho.response.form.FormFiles;
import aho.service.RequestAddService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RequestAddServiceImpl implements RequestAddService {
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final AreaRepository areaRepository;
    @Autowired
    private final EquipmentRepository equipmentRepository;
    @Autowired
    private final StatusRepository statusRepository;
    @Autowired
    private final RequestRepository requestRepository;
    @Autowired
    private final SettingsRepository settingsRepository;
    @Override
    public ResponseUser getUsersRequest(){
        ResponseUser responseUser = new ResponseUser();
        responseUser.setUserDataList(new ArrayList<>());
        List<User> listUser = userRepository.findAllCreators();
        listUser.forEach(u -> {
            CommonData commonData = new CommonData();
            commonData.setId(u.getId());
            commonData.setName(u.getFio());
            responseUser.getUserDataList().add(commonData);
        });
        responseUser.setResult(true);
        return responseUser;
    }
   @Override
    public ResponseUser getUsersStatus(){
        ResponseUser responseUser = new ResponseUser();
        responseUser.setUserDataList(new ArrayList<>());
        List<User> listUser = userRepository.findAllContrillers();
        listUser.forEach(u -> {
            CommonData commonData = new CommonData();
            commonData.setId(u.getId());
            commonData.setName(u.getFio());
            responseUser.getUserDataList().add(commonData);
        });
        responseUser.setResult(true);
        return responseUser;
    }
    @Override
    public ResponseEquipment getEquipmentL1() {
        ResponseEquipment responseEquipment = new ResponseEquipment();
        responseEquipment.setListEquipment(new ArrayList<>());
        List<Equipment> listEquipment = equipmentRepository.findAllByCategory(0);
        listEquipment.forEach(e ->{
            CommonData commonData = new CommonData();
            commonData.setId(e.getId());
            commonData.setName(e.getName());
            responseEquipment.getListEquipment().add(commonData);
        });
        responseEquipment.setResult(true);
        return responseEquipment;
    }

    @Override
    public ResponseEquipment getEquipmentL2(String category) {
        ResponseEquipment responseEquipment = new ResponseEquipment();
        responseEquipment.setListEquipment(new ArrayList<>());
        List<Equipment> listEquipment = equipmentRepository.findEquipmentInCategoryById(category);
        listEquipment.forEach(e ->{
            CommonData commonData = new CommonData();
            commonData.setId(e.getId());
            commonData.setName(e.getName());
            responseEquipment.getListEquipment().add(commonData);
        });
        responseEquipment.setResult(true);
        return responseEquipment;
    }

    @Override
    public ResponseArea getArea() {
        ResponseArea responseArea = new ResponseArea();
        responseArea.setListArea(new ArrayList<>());
        List<Area> listArea = areaRepository.findAll();
        listArea.forEach(a -> {
            CommonData commonData = new CommonData();
            commonData.setId(a.getId());
            commonData.setName(a.getName());
            responseArea.getListArea().add(commonData);
        });
        responseArea.setResult(true);
        return responseArea;
    }
    @Override
    public ResponseSettings getWayStorage(){
        ResponseSettings response = new ResponseSettings();
        List<Settings> listSettings = settingsRepository.findAllByName("storage");
        listSettings.forEach(s->{
            CommonData commonData = new CommonData();
            commonData.setId(s.getId());
            commonData.setName(s.getName());
            response.getListSettings().add(commonData);
        });
        response.setResult(true);
        return response;
    }
    @Override
    public ResponseDate getCurrentDate() {
        ResponseDate response = new ResponseDate();
        response.setCurrentDate(LocalDate.now());
        response.setResult(true);
        return response;
    }

    @Override
    public ResponseStatus getStatus(){
        ResponseStatus responseStatus = new ResponseStatus();
        responseStatus.setListStatus(new ArrayList<>());
        List<Status> listStatus = statusRepository.findAll();
        listStatus.forEach(s-> {
            CommonData commonData = new CommonData();
            commonData.setId(s.getId());
            commonData.setName(s.getName());
            responseStatus.getListStatus().add(commonData);
        });
        responseStatus.setResult(true);
        return responseStatus;
    }
    @Override
    public ResponseCommon writeFiles(String requestId, MultipartFile[] files){
        ResponseCommon response = new ResponseCommon();
        List<Settings> listSettings = settingsRepository.findAllByName("storage");
        final String[] way = new String[1];
        listSettings.forEach(s -> way[0] = s.getValues());
        String archiveDir = way[0] + "/" + requestId;
        if (!(Paths.get(archiveDir).toFile().exists())){
            try {
                Files.createDirectory(Paths.get(archiveDir));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        for(MultipartFile file:files){
            try {
                Files.copy(file.getInputStream(), Paths.get(archiveDir + "/" + Paths.get(file.getOriginalFilename()).toString()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        Request request = requestRepository.findById(Integer.parseInt(requestId)).get();
        request.setIsJoinedFiles(true);
        requestRepository.save(request);
        response.setResult(true);
        return response;
    }
    @Override
    public ResponseJournal writeRequest(FormAddRequest formAddRequest){
        ResponseJournal response = new ResponseJournal();
        AreaComparator areaComparator = new AreaComparator();
        UserComparator userComparator = new UserComparator();
        StatusComparator statusComparator = new StatusComparator();
        Area.listArea.sort(areaComparator);
        User.listUsers.sort(userComparator);
        Status.listStatus.sort(statusComparator);
        Area testArea = new Area();
        testArea.setId(Integer.parseInt(formAddRequest.getArea()));
        User testUser = new User();
        testUser.setId(Integer.parseInt(formAddRequest.getUser()));
        Status testStatus = new Status();
        testStatus.setName("Неопределено");
        Equipment testEquipmentL1 = null;
        System.out.println(formAddRequest.getEquipmentL1());
        testEquipmentL1 = equipmentRepository.findById(Integer.parseInt(formAddRequest.getEquipmentL1())).get();
        Equipment testEquipmentL2 = null;
        testEquipmentL2 = equipmentRepository.findById(Integer.parseInt(formAddRequest.getEquipmentL2())).get();
        Request request = null;
        if (formAddRequest.getRequestId().equals("0")){
            request = new Request();
        } else{
            request = requestRepository.findById(Integer.parseInt(formAddRequest.getRequestId())).get();
        }

        request.setArea(Area.listArea.get(Collections.binarySearch(Area.listArea, testArea, areaComparator)));
        request.setCategory(testEquipmentL1);
        if (testEquipmentL2.getId() != 0) {
            request.setEquipment(testEquipmentL2);
        }
        request.setUser(User.listUsers.get(Collections.binarySearch(User.listUsers, testUser, userComparator)));
        request.setNormativeCount(Integer.parseInt(formAddRequest.getNormativeCount()));
        request.setFactCount(Integer.parseInt(formAddRequest.getFactCount()));
        request.setSpisCount(Integer.parseInt(formAddRequest.getSpisCount()));
        request.setPlanedCount(Integer.parseInt(formAddRequest.getPlanedCount()));
        request.setComment(formAddRequest.getDescription());
        request.setStatus(Status.listStatus.get(Collections.binarySearch(Status.listStatus, testStatus, statusComparator)));
        request.setDate(LocalDateTime.parse(formAddRequest.getDateRequest(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        requestRepository.save(request);
        requestRepository.findLatInsertId().forEach(r -> {
            ResponseJournalData journalData = new ResponseJournalData(r);
            response.getListJournalData().add(journalData);
        });
        response.setResult(true);
        return response;
    }

    @Override
    public ResponseCommon addEquipment(FormEquipment formEquipment){
        ResponseCommon response = new ResponseCommon();
        Equipment equipment = new Equipment();
        equipment.setName(formEquipment.getName());
        List<Equipment> listEquipment = equipmentRepository.findAllByName(formEquipment.getName());
        if (listEquipment.size() == 0){
            List<Equipment> listCategory = equipmentRepository.findAllByName(formEquipment.getCategory());
            equipment.setCategory(String.valueOf(listCategory.get(0).getId()));
            equipmentRepository.save(equipment);
        }
        response.setResult(true);
        return response;
    }
}
