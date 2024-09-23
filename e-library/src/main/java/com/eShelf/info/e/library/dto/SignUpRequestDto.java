package com.eShelf.info.e.library.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignUpRequestDto {
    private String name;
    private String emailId;
    private String password;
    private int employeeId;
    private String role;
}
