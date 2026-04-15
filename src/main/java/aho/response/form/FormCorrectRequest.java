package aho.response.form;

import lombok.Data;

@Data
public class FormCorrectRequest {
    private int id;
    private String status;
    private String dateStatus;
    private String userStatus;
    private String comment;
}
