package aho.service.impl;

import aho.model.Area;
import aho.model.Status;
import aho.model.User;
import aho.model.UserComparator;
import aho.repository.AreaRepository;
import aho.repository.StatusRepository;
import aho.repository.UserRepository;
import aho.response.*;
import aho.service.AuthorisationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthorisationServiceImpl implements AuthorisationService {

    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final AreaRepository areaRepository;
    @Autowired
    private final StatusRepository statusRepository;
    public static User currentUser;
    @Override
    public ResponseUser getUsers(){
        ResponseUser responseUser = new ResponseUser();
        responseUser.setUserDataList(new ArrayList<>());
        List<User> listUser = userRepository.findAll();
        UserComparator userComparator = new UserComparator();
        listUser.sort(userComparator);
        listUser.forEach(u -> {
            System.out.println(u.getFio());
            CommonData commonData = new CommonData();
            commonData.setId(u.getId());
            commonData.setName(u.getFio());
            responseUser.getUserDataList().add(commonData);
        });
        responseUser.setResult(true);
        return responseUser;
    }

    @Override
    public ResponseStatus getUserStatus() {
        ResponseStatus response = new ResponseStatus();
        CommonData commonData = new CommonData();
        commonData.setName(String.valueOf(AuthorisationServiceImpl.currentUser.getRole()));
        response.getListStatus().add(commonData);
        response.setResult(true);
        return response;
    }

    @Override
    public ResponseStatus getUserName() {
        ResponseStatus response = new ResponseStatus();
        CommonData commonData = new CommonData();
        commonData.setName(String.valueOf(AuthorisationServiceImpl.currentUser.getFio()));
        response.getListStatus().add(commonData);
        response.setResult(true);
        return response;
    }

    @Override
    public ResponseVerification verification(String user, String password){
        ResponseVerification response = new ResponseVerification();
        Optional<User> listUser = userRepository.findById(Integer.parseInt(user));
        if (listUser.get().getPassword().equals(password)) {
            currentUser = listUser.get();
            Area.listArea.addAll(areaRepository.findAll());
            Status.listStatus.addAll(statusRepository.findAll());
            User.listUsers.addAll(userRepository.findAll());
            AuthorisationServiceImpl.currentUser = listUser.get();
            response.setResult(true);
            response.setStatus(String.valueOf(listUser.get().getRole()));
            response.setId(String.valueOf(listUser.get().getId()));
        } else{
            response.setResult(false);
        }
        return response;
    }
}
