package aho.response.form;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
@Data
public class FormFiles {
    private String requestId;
    private List<String> listFiles = new ArrayList<String>();
}
