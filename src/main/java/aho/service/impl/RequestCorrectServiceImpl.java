package aho.service.impl;

import aho.model.*;
import aho.repository.RequestRepository;
import aho.repository.SettingsRepository;
import aho.response.ResponseDownload;
import aho.response.ResponseCommon;
import aho.response.ResponseJournal;
import aho.response.ResponseJournalData;
import aho.response.form.FormCorrectRequest;
import aho.service.RequestCorrectService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.*;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service
@RequiredArgsConstructor
public class RequestCorrectServiceImpl implements RequestCorrectService {

    @Autowired
    private final RequestRepository requestRepository;
    @Autowired
    private final SettingsRepository settingsRepository;
    Request request = new Request();
    @Override
    public byte[] downloadFiles(String requestId) {
        ResponseDownload response = new ResponseDownload();
        List<Settings> listSettings = settingsRepository.findAllByName("storage");
        final String[] way = new String[1];
        listSettings.forEach(s -> way[0] = s.getValues());
        String archiveDir = way[0] + "/" + requestId;
        File[] files = Paths.get(archiveDir).toFile().listFiles();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {

            ZipOutputStream zout = new ZipOutputStream(baos);
            for (File file:files) {
                String fileName = file.getName();

                ZipEntry zipEntry = new ZipEntry(fileName);
                zout.putNextEntry(zipEntry);

                FileInputStream fis = new FileInputStream(file);
                byte[] buffer = new byte[fis.available()];
                fis.read(buffer);

                zout.write(buffer);

            }
            zout.closeEntry();
            zout.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Paths.get(archiveDir).toFile().listFiles();
        return baos.toByteArray();
    }
    @Override
    public ResponseJournal getRequest(int id) {
        Optional<Request> requestList = requestRepository.findById(id);
        request = requestList.get();
        ResponseJournal response = new ResponseJournal();
        ResponseJournalData responseData = new ResponseJournalData(request);
        responseData.setCategory(request.getCategory().getName());
        if (request.getEquipment() != null) responseData.setEquipment(request.getEquipment().getName());
            else responseData.setEquipment("");
        responseData.setComment(request.getComment());
        response.getListJournalData().add(responseData);
        response.setResult(true);
        return response;
    }
    @Override
    public ResponseCommon writeRequest(FormCorrectRequest formCorrectRequest) {
        ResponseCommon response = new ResponseCommon();
        UserComparator userComparator = new UserComparator();
        StatusComparator statusComparator = new StatusComparator();
        User.listUsers.sort(userComparator);
        Status.listStatus.sort(statusComparator);
        Optional<Request> requestList = requestRepository.findById(formCorrectRequest.getId());
        request = requestList.get();
        User testUser = new User();
        testUser.setId(Integer.parseInt(formCorrectRequest.getUserStatus()));
        request.setUserStatus(User.listUsers.get(Collections.binarySearch(User.listUsers, testUser, userComparator)));
        Status testStatus = new Status();
        testStatus.setName(formCorrectRequest.getStatus());
        request.setStatus(Status.listStatus.get(Collections.binarySearch(Status.listStatus, testStatus, statusComparator)));
        request.setDateStatus(LocalDateTime.now());
        request.setCommentController(formCorrectRequest.getComment());
        requestRepository.save(request);
        response.setResult(true);
        return response;
    }
}
