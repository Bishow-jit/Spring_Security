package com.example.SpringSecurity.Controller;

import com.example.SpringSecurity.Configuration.UserInfoDetailService;
import com.example.SpringSecurity.Dto.LoginForm;
import com.example.SpringSecurity.Entity.UserInfo;
import com.example.SpringSecurity.Dto.User;
import com.example.SpringSecurity.Service.JwtService;
import com.example.SpringSecurity.Service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class ApplicationController {

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserInfoDetailService userInfoDetailService;
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
        for( int i = 1 ; i< 10; i++){
           User user = User.builder()
                    .id(i)
                    .name("user "+i)
                    .email("user"+i+"@xyz.com")
                    .dob("0"+i+"-0"+i+"-200"+i).build();
            userList.add(user);
        }
        return userList;
    }

    @GetMapping(value = "/getDetails/{id}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public User getDetails(@PathVariable("id")Integer id){
        User user = new User();
        user.setId(id);
        user.setName("user"+id);
        user.setEmail("user"+id+"@xyy.com");
        user.setDob("0"+id+"-0"+id+"-200"+id);

        return user;
    }

    @GetMapping(value ="/hello")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public String demoHello(){
        return "HELLO BISHOWJIT";
    }

    @PostMapping(value = "/noAuth/user/add", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addUser(@RequestBody UserInfo userInfo){
        String response = userInfoService.addUser(userInfo);
        return ResponseEntity.status(HttpStatus.OK).body(response);

    }

    @GetMapping(value = "/getUser")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?>getUserDetails(@RequestParam("username") String username){
            UserInfo userInfo = userInfoService.getUser(username);
            if(userInfo != null){
                return ResponseEntity.status(HttpStatus.OK).body(userInfo);
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User Not Found");


    }

    @PostMapping(value = "/login")
    public String login(@RequestBody LoginForm loginForm){
        try{
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    loginForm.username(),loginForm.password()));
            if(authentication.isAuthenticated()){
                return jwtService.generateToken(userInfoDetailService.loadUserByUsername(loginForm.username()));
            }else {
                throw new UsernameNotFoundException("Invalid Credentials");
            }
        }catch (Exception e ){
            e.printStackTrace();
            return  "Failed";
        }
    }
    @GetMapping(value = "/user/home")
    public ResponseEntity<?> getUserHome(){
        return ResponseEntity.status(HttpStatus.OK).body("WELCOME TO USER HOME");
    }

    @GetMapping(value = "/admin/home")
    public ResponseEntity<?>getAdminHome(){
        return ResponseEntity.status(HttpStatus.OK).body("*********WELCOME TO ADMIN HOME*********");
    }


}
