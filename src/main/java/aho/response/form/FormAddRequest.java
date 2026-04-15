package aho.response.form;

import lombok.Data;

@Data
public class FormAddRequest {
    private String dateRequest;
    private String user;
    private String area;
    private String equipmentL1;
    private String equipmentL2;
    private String normativeCount;
    private String factCount;
    private String spisCount;
    private String planedCount;
    private String description;
    private String requestId;
}
