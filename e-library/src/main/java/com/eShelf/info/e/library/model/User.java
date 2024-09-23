package com.eShelf.info.e.library.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Entity(name = "Library_User")
@Getter
@Setter
public class User extends BaseModel{
    private String name;
    private String emailId;
    private String password;
    private int employeeId;
    private String role;
    private int dueAmount;

    public User() {
    }
    public User(String name, String emailId, String password, int employeeId, String role) {
        this.name = name;
        this.emailId = emailId;
        this.password = password;
        this.employeeId = employeeId;
        this.role = role;
        this.dueAmount = 0;
    }


}

