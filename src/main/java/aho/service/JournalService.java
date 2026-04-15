package aho.service;

import aho.response.ResponseJournal;
import aho.response.form.FormJournal;

public interface JournalService {
    ResponseJournal getJournalElements(FormJournal formJournal);
}
