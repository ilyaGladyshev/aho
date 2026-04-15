package aho.controller;

import aho.response.*;
import aho.response.ResponseStatus;
import aho.response.form.*;
import aho.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;

@RestController
//@RequestMapping("/api")
@RequiredArgsConstructor
public class ApiController {

    private final AuthorisationService authorisationService;
    private final RequestAddService requestAddService;
    private final JournalService journalService;
    private final RequestCorrectService requestCorrectService;
    private final ClosingService closingService;
    @GetMapping("/usersVerification")
    public  ResponseEntity<ResponseUser> fillListUsers(){
        return ResponseEntity.ok(authorisationService.getUsers());
    }
    @GetMapping("/areasRequest")
    public ResponseEntity<ResponseArea> fillListArea(){return ResponseEntity.ok(requestAddService.getArea());}
    @GetMapping("/usersCreator")
    public ResponseEntity<ResponseUser> fillListUsersRequest(){return ResponseEntity.ok(requestAddService.getUsersRequest());}
    @GetMapping("/usersController")
    public ResponseEntity<ResponseUser> fillListUsersStatus(){return ResponseEntity.ok(requestAddService.getUsersStatus());}
    @GetMapping("/equipmentL1")
    public  ResponseEntity<ResponseEquipment> fillListEquipmentL1(){
         return ResponseEntity.ok(requestAddService.getEquipmentL1());
    }

    @GetMapping("/getCurrentStatus")
    public  ResponseEntity<ResponseStatus> getCurrentStatus() {
        return  ResponseEntity.ok(authorisationService.getUserStatus());
    }

    @GetMapping("/getWayStorage")
    public  ResponseEntity<ResponseSettings> getWayStorage(){
        return  ResponseEntity.ok(requestAddService.getWayStorage());
    }
    @GetMapping("/getCurrentName")
    public ResponseEntity<ResponseStatus> getCurrentName() {
        return  ResponseEntity.ok(authorisationService.getUserName());
    }
    @GetMapping("/currentDate")
    public ResponseEntity<ResponseDate> getCurrentDate(){
        return ResponseEntity.ok(requestAddService.getCurrentDate());
    }

    @GetMapping("/status")
    public ResponseEntity<ResponseStatus> fillListStatus(){
        return ResponseEntity.ok(requestAddService.getStatus());
    }
    @GetMapping("/equipmentL2")
    public ResponseEntity<ResponseEquipment> fillListEquipmentL2(@RequestParam String idCategory){
        return ResponseEntity.ok(requestAddService.getEquipmentL2(URLDecoder.decode(idCategory)));
    }

    private String formatDate(String str){
        str = URLDecoder.decode(str);
        if (str != "") {
            return str.concat(" 00:00:00");
        } else {
            return str;
        }
    }
    @GetMapping("/journalRequest")
    public  ResponseEntity<ResponseJournal> fillJournalRequest(@RequestParam String dateFrom, @RequestParam String dateTo, @RequestParam String area,
                                                               @RequestParam String userCreator, @RequestParam String status, @RequestParam String category,
                                                               @RequestParam String equipment, @RequestParam String statistics, @RequestParam String userInspector,
                                                               @RequestParam String currentUser){
        FormJournal formJournal = new FormJournal();
        formJournal.setDateTo(formatDate(dateTo));
        formJournal.setDateFrom(formatDate(dateFrom));
        formJournal.setStatus(status);
        formJournal.setArea(area);
        formJournal.setEquipment(equipment);
        formJournal.setCategory(category);
        formJournal.setUserCreator(userCreator);
        formJournal.setStatistics(statistics);
        formJournal.setUserInspector(userInspector);
        formJournal.setCurrenUser(currentUser);
        return ResponseEntity.ok(journalService.getJournalElements(formJournal));
    }

    @GetMapping("/correctRequestShow")
    public ResponseEntity<ResponseJournal> correctRequestShow(@RequestParam int id){
        return ResponseEntity.ok(requestCorrectService.getRequest(id));
    }
    @PostMapping("/verification")
    public  ResponseEntity<ResponseVerification> verification(@RequestBody FormVerification formData) {
        return ResponseEntity.ok(authorisationService.verification(formData.getItemId(), formData.getItemText()));
    }
    @PostMapping("/addEquipment")
    public  ResponseEntity<ResponseCommon> addEquipment(@RequestBody FormEquipment formEquipment){
        return ResponseEntity.ok(requestAddService.addEquipment(formEquipment));
    }
    @PostMapping("/addRequest")
    public  ResponseEntity<ResponseJournal> addRequest(@RequestBody FormAddRequest formData)  {
        return ResponseEntity.ok(requestAddService.writeRequest(formData));
    }
    @PostMapping("/correctRequest")
    public  ResponseEntity<ResponseCommon> correctRequest(@RequestBody FormCorrectRequest formData)  {
        return ResponseEntity.ok(requestCorrectService.writeRequest(formData));
    }
    @PostMapping("/writeFiles")
    public  ResponseEntity<ResponseCommon> writeFiles(@RequestParam("requestId") String requestId,
                                                      @RequestParam("files") MultipartFile[] files)  {
        return ResponseEntity.ok(requestAddService.writeFiles(requestId, files));
    }
    @PostMapping("/downloadFiles")
    public  ResponseEntity<byte[]> writeFiles(@RequestParam("requestId") String requestId) {
        byte[] archive = requestCorrectService.downloadFiles(requestId);
        final String filename = LocalDate.now().toString() + "_" + requestId;
        ContentDisposition contentDisposition = ContentDisposition.inline()
                .filename(filename + ".zip", StandardCharsets.UTF_8)
                .build();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentDisposition(contentDisposition);
        httpHeaders.setContentType(MediaType.valueOf(MediaType.APPLICATION_OCTET_STREAM_VALUE));
        return ResponseEntity.ok()
                .headers(httpHeaders)
                .body(archive);
        //return ResponseEntity.ok(requestCorrectService.downloadFiles(requestId));
    }
    @GetMapping("/closing")
    public  ResponseEntity<ResponseCommon> closing(){
        return ResponseEntity.ok(closingService.closeRequest());
    }
}
