package aho.response.form;

import lombok.Data;

@Data
public class FormJournal {
    private String dateFrom;
    private String dateTo;
    private String area;
    private String userCreator;
    private String status;
    private String category;
    private String equipment;
    private String statistics;
    private String userInspector;
    private String currenUser;
}
