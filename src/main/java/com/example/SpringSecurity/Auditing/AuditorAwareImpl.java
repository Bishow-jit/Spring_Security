package com.example.SpringSecurity.Auditing;

import com.example.SpringSecurity.Configuration.UserInfoDetailService;
import com.example.SpringSecurity.Entity.UserInfo;
import com.example.SpringSecurity.Repository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;


public class AuditorAwareImpl implements AuditorAware {

//    @Autowired
//    private UserDetails userDetails;


    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.of("Bishow101");
    }
}
