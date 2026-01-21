package com.project.internal_request_system.dto;

import lombok.Data;

@Data
public class UserRegistrationDto {
    private String username;
    private String password;
    private String fullName;
    private String email;
    private String departmentName;
    private String roleName;
}