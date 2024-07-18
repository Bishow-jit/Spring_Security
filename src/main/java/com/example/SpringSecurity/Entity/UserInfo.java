package com.example.SpringSecurity.Entity;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfo extends BaseModel {

    private String username;

    private String password;

    private String name;

    private String email;

    private String roles;
}
