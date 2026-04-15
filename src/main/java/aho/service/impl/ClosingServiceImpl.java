package aho.service.impl;

import aho.model.Request;
import aho.model.Status;
import aho.repository.RequestRepository;
import aho.repository.StatusRepository;
import aho.response.ResponseCommon;
import aho.service.ClosingService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ClosingServiceImpl implements ClosingService {
    @Autowired
    private final RequestRepository requestRepository;
    @Autowired
    private final StatusRepository statusRepository;
    private Boolean closeResponses(){
        Boolean result = false;
        Status status = statusRepository.findAllByName("Закрыто").get(0);
        try {
            List<Request> listRequest = requestRepository.findAllUnclosing();
            listRequest.forEach(r ->{
                r.setStatus(status);
                r.setDateStatus(LocalDateTime.now());
                r.setUserStatus(AuthorisationServiceImpl.currentUser);
                requestRepository.save(r);
            });
            result = true;
        } catch(Exception ex){
        }
        return result;
    }
    @Override
    public ResponseCommon closeRequest() {
        ResponseCommon response = new ResponseCommon();
        if (closeResponses()){
            response.setResult(true);
        }
        return response;
    }
}
