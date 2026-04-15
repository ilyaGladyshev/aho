package aho.response;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ResponseJournal {
    private boolean result;
    private List<ResponseJournalData> listJournalData = new ArrayList<>();
}
