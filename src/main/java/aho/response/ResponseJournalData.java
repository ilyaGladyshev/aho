package aho.response;

import aho.model.Request;
import lombok.Data;

import java.time.format.DateTimeFormatter;

@Data
public class ResponseJournalData {
    private int id;
    private int number;
    private String dateRequest;
    private String userCreator;
    private String Area;
    private String equipment;
    private int normativeCount;
    private int factCount;
    private int spisCount;
    private int planedCount;
    private String status;
    private String userStatus;
    private String dateStatus;
    private String category;
    private String comment;
    private String commentController;
    private int isJoinedFiles;
    public ResponseJournalData(){
    }
    public ResponseJournalData(Request request){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
        this.setId(request.getId());
        this.setArea(request.getArea().getName());
        this.setUserCreator(request.getUser().getFio());
        if (request.getEquipment() == null){
            this.setEquipment(request.getCategory().getName() + "; "  + request.getComment());
        } else {
            this.setEquipment(request.getCategory().getName() + "; " + request.getEquipment().getName() +
                    "; " + request.getComment());
        }
        this.setDateRequest(request.getDate().format(formatter));
        this.setNormativeCount(request.getNormativeCount());
        this.setFactCount(request.getFactCount());
        this.setSpisCount(request.getSpisCount());
        this.setPlanedCount(request.getPlanedCount());
        this.setStatus(request.getStatus().getName());
        if (request.getIsJoinedFiles()){
            this.setIsJoinedFiles(1);
        } else{
            this.setIsJoinedFiles(0);
        }
        if (request.getUserStatus() != null) {
            this.setUserStatus(request.getUserStatus().getFio());
        } else {
            this.setUserStatus("");
        }
        if (request.getDateStatus() != null){
            this.setDateStatus(request.getDateStatus().format(formatter));
        } else {
            this.setDateStatus("");
        }
        this.setCommentController(request.getCommentController());
    }
}
