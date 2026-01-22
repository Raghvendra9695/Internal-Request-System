package com.project.internal_request_system.dto;

import lombok.Data;

@Data
public class RequestActionDto {
    private Long requestId;
    private Long approverUserId;
    private String action;
    private String comment;
}