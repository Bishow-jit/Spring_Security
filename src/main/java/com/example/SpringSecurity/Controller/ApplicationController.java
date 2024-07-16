package com.example.SpringSecurity.Controller;

import com.example.SpringSecurity.User;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class ApplicationController {

    @GetMapping(value = "/noAuth")
    public String noAuthApi(){
        return "This end point is not authenticated";
    }

    @GetMapping(value = "/auth")
    public String authApi(){
        return "This end point is Authenticated";
    }

    @GetMapping(value = "/auth/getAllUsers")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public List<User> getAllUser(){
        List<User> userList = new ArrayList<>();
        for( int i = 0 ; i< 10; i++){
           User user = User.builder()
                    .id(i)
                    .name("user "+i)
                    .email("user"+i+"@xyz.com")
                    .dob("0"+i+"-0"+i+"-200"+i).build();
            userList.add(user);
        }
        return userList;
    }

    @GetMapping(value = "/auth/getDetails/{id}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public User getDetails(@PathVariable("id")Integer id){
        User user = new User();
        user.setId(id);
        user.setName("user"+id);
        user.setEmail("user"+id+"@xyy.com");
        user.setDob("0"+id+"-0"+id+"-200"+id);

        return user;
    }
}
