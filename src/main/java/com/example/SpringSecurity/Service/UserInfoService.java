package com.example.SpringSecurity.Service;

import com.example.SpringSecurity.Entity.UserInfo;
import com.example.SpringSecurity.Repository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserInfoService {

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public String addUser(UserInfo userInfo){
        userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword()));
        UserInfo userInfo1 = userInfoRepository.save(userInfo);

        if(userInfo1 != null){
            return "New user has been saved";
        }

        return "Failed";
    }

    public UserInfo getUser(String username){

        Optional<UserInfo> userInfo = userInfoRepository.findByUsername(username);
        if(userInfo.isPresent()){
            return userInfo.get();
        }

        return null;
    }
}
