package aho.service;

import aho.response.ResponseCommon;
import aho.response.ResponseStatus;
import aho.response.ResponseUser;
import aho.response.ResponseVerification;

public interface AuthorisationService {
    ResponseUser getUsers();
    ResponseStatus getUserStatus();
    ResponseStatus getUserName();
    ResponseVerification verification(String user, String password);
}
