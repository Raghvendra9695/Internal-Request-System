package com.project.internal_request_system.dto;

import lombok.Data;

@Data
public class RequestDto {
    private Long userId;
    private String requestTypeName;
    private String subject;
    private String description;
}