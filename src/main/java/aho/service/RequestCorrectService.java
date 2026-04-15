package aho.service;

import aho.response.ResponseCommon;
import aho.response.ResponseDownload;
import aho.response.ResponseJournal;
import aho.response.form.FormCorrectRequest;

public interface RequestCorrectService {
    ResponseJournal getRequest(int id);
    ResponseCommon writeRequest(FormCorrectRequest formCorrectRequest);
    byte[] downloadFiles(String requestId);
}
