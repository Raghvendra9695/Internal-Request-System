package com.project.internal_request_system.controller;
import com.project.internal_request_system.dto.RequestActionDto;
import com.project.internal_request_system.dto.RequestDto;
import com.project.internal_request_system.entity.Request;
import com.project.internal_request_system.service.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/requests")
@CrossOrigin(origins = "*")
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

    @PostMapping("/process")
    public ResponseEntity<?> processRequest(@RequestBody RequestActionDto dto) {
        try {
            Request request = requestService.processRequest(dto);
            return ResponseEntity.ok("Request Processed! New Status: " + request.getStatus());
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping("/my-requests/{userId}")
    public ResponseEntity<?> getMyRequests(@PathVariable Long userId) {
        return ResponseEntity.ok(requestService.getRequestsForUser(userId));
    }
    @GetMapping("/pending/{role}")
    public ResponseEntity<?> getPendingRequests(@PathVariable String role) {
        return ResponseEntity.ok(requestService.getPendingRequestsForRole(role));
    }
}