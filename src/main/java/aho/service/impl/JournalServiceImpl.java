package aho.service.impl;

import aho.model.*;
import aho.repository.*;
import aho.response.ResponseJournal;
import aho.response.ResponseJournalData;
import aho.response.form.FormJournal;
import aho.service.JournalService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class JournalServiceImpl implements JournalService {

    private int number = 0;
    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final EquipmentRepository equipmentRepository;
    @Autowired
    private final AreaRepository areaRepository;
    @Autowired
    private final StatusRepository statusRepository;
    private List<Request> getRequestList(FormJournal formJournal){
        List<Request> listRequest = null;
        User currentUser = userRepository.findById(Integer.parseInt(formJournal.getCurrenUser())).get();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String query = "SELECT r from Request r ";
        String request = "";
        if (!formJournal.getArea().equals("")) request += "and r.area = :area ";
        if (!formJournal.getEquipment().equals("")) request +="and r.equipment = :equipment ";
        if (!formJournal.getCategory().equals("")) request += "and r.category = :category ";
        if (!formJournal.getUserCreator().equals("")) request += "and r.user = :user ";
        if (!formJournal.getUserInspector().equals("")) request += "and r.userStatus = :userStatus ";
        if (!formJournal.getStatus().equals("")) request += "and r.status = :status ";
        if (!formJournal.getDateFrom().equals(""))  request +="and r.date >= :dateFrom ";
        if (!formJournal.getDateTo().equals("")) request += "and r.date <= :dateTo ";
        if (!(formJournal.getStatistics().equals("0"))) request += "and r.status = :completeStatus ";
        if (formJournal.getStatistics().equals("0")) request += "and r.status not in :listStatus ";
        if ((currentUser.getRole() == 1) && (currentUser.getOtdel() == 0)) request += "and r.user = :currentUser ";
        if ((currentUser.getRole() == 1) && (currentUser.getOtdel() != 0)) request += "and r.user.otdel = :currentOtdel ";
        if (!request.equals("")) request = "where " + request.substring(3);
        query+= request;
        TypedQuery<Request> typedQuery =  entityManager.createQuery(query,  Request.class);
        if (formJournal.getStatistics().equals("0")){
            List<Status> listStatus = new ArrayList<>();
            listStatus.add(statusRepository.findById(2).get());
            listStatus.add(statusRepository.findById(3).get());
            listStatus.add(statusRepository.findById(4).get());
            typedQuery.setParameter("listStatus", listStatus);
        }
        else {
            Status completeStatus = statusRepository.findById(Integer.parseInt(formJournal.getStatistics())).get();
            typedQuery.setParameter("completeStatus", completeStatus);
        }
        if ((currentUser.getRole() == 1) && (currentUser.getOtdel() == 0)){
            typedQuery.setParameter("currentUser", currentUser);
        }
        if ((currentUser.getRole() == 1) && (currentUser.getOtdel() != 0)){
            typedQuery.setParameter("currentOtdel", currentUser.getOtdel());
        }
        if ((!formJournal.getArea().equals(""))){
            Area area = areaRepository.findById(Integer.parseInt(formJournal.getArea())).get();
            typedQuery.setParameter("area", area);
        }
        if ((!formJournal.getEquipment().equals(""))){
            Equipment equipment = equipmentRepository.findById(Integer.parseInt(formJournal.getEquipment())).get();
            typedQuery.setParameter("equipment", equipment);
        }
        if ((!formJournal.getCategory().equals(""))){
            Equipment category = equipmentRepository.findById(Integer.parseInt(formJournal.getCategory())).get();
            typedQuery.setParameter("category", category);
        }
        if ((!formJournal.getUserCreator().equals(""))){
            User user = userRepository.findById(Integer.parseInt(formJournal.getUserCreator())).get();
            typedQuery.setParameter("user", user);
        }
        if ((!formJournal.getUserInspector().equals(""))){
            User userInspector = userRepository.findById(Integer.parseInt(formJournal.getUserInspector())).get();
            typedQuery.setParameter("userStatus", userInspector);
        }
        if ((!formJournal.getStatus().equals(""))){
            Status status = statusRepository.findById(Integer.parseInt(formJournal.getStatus())).get();
            typedQuery.setParameter("status", status);
        }
        if ((!formJournal.getDateFrom().equals(""))){
            LocalDateTime dateFrom = LocalDateTime.parse(formJournal.getDateFrom(), formatter);
            typedQuery.setParameter("dateFrom", dateFrom);
        }
        if ((!formJournal.getDateTo().equals(""))){
            LocalDateTime dateTo = LocalDateTime.parse(formJournal.getDateTo(), formatter);
            typedQuery.setParameter("dateTo", dateTo);
        }
        listRequest = typedQuery.getResultList();
        return listRequest;
    }
    @Override
    public ResponseJournal getJournalElements(FormJournal formJournal){
        ResponseJournal responseJournal = new ResponseJournal();
        List<Request> requestList = getRequestList(formJournal);
        if (requestList != null){
            requestList.forEach(request -> {
                number++;
                ResponseJournalData journalData = new ResponseJournalData(request);
                journalData.setNumber(number);
                responseJournal.getListJournalData().add(journalData);
            });
            responseJournal.setResult(true);
        }
        return responseJournal;
    }
}
