package com.project.internal_request_system.controller;

import com.project.internal_request_system.dto.RequestDto;
import com.project.internal_request_system.entity.Request;
import com.project.internal_request_system.service.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/requests")
public class RequestController {

    @Autowired
    private RequestService requestService;
    @PostMapping("/create")
    public ResponseEntity<?> createRequest(@RequestBody RequestDto dto) {
        try {
            Request request = requestService.createRequest(dto);
            return ResponseEntity.ok("Request Submitted Successfully! ID: " + request.getId());
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}