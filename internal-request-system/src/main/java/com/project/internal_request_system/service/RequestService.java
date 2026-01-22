package com.project.internal_request_system.service;

import com.project.internal_request_system.dto.RequestActionDto;
import com.project.internal_request_system.dto.RequestDto;
import com.project.internal_request_system.entity.*;
import com.project.internal_request_system.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RequestService {

    @Autowired
    private RequestRepository requestRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RequestTypeRepository requestTypeRepository;

    @Autowired
    private RequestLogRepository requestLogRepository;
    @Transactional
    public Request createRequest(RequestDto dto) {
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));


        RequestType type = requestTypeRepository.findByName(dto.getRequestTypeName());
        if (type == null) {
            throw new RuntimeException("Invalid Request Type: " + dto.getRequestTypeName());
        }

        Request request = new Request();
        request.setUser(user);
        request.setRequestType(type);
        request.setSubject(dto.getSubject());
        request.setDescription(dto.getDescription());
        request.setStatus(RequestStatus.PENDING);
        request.setCurrentApproverRole("ROLE_HOD");

        Request savedRequest = requestRepository.save(request);
        RequestLog log = new RequestLog();
        log.setRequest(savedRequest);
        log.setAction("CREATED");
        log.setComment("Request initiated by user.");
        log.setActionByUser(user);

        requestLogRepository.save(log);

        return savedRequest;
    }
    @Transactional
    public Request processRequest(RequestActionDto dto) {
        Request request = requestRepository.findById(dto.getRequestId())
                .orElseThrow(() -> new RuntimeException("Request not found"));

        User approver = userRepository.findById(dto.getApproverUserId())
                .orElseThrow(() -> new RuntimeException("Approver not found"));
        if ("REJECT".equalsIgnoreCase(dto.getAction())) {
            request.setStatus(RequestStatus.REJECTED);
            request.setCurrentApproverRole(null);
        }
        else if ("APPROVE".equalsIgnoreCase(dto.getAction())) {
            boolean needsPrincipal = request.getRequestType().getNeedsPrincipalApproval();
            if (request.getStatus() == RequestStatus.PENDING && needsPrincipal) {
                request.setStatus(RequestStatus.APPROVED_BY_HOD);
                request.setCurrentApproverRole("ROLE_PRINCIPAL");
            } else {
                request.setStatus(RequestStatus.COMPLETED);
                request.setCurrentApproverRole(null);
            }
        } else {
            throw new RuntimeException("Invalid Action. Use APPROVE or REJECT");
        }
        Request updatedRequest = requestRepository.save(request);
        RequestLog log = new RequestLog();
        log.setRequest(updatedRequest);
        log.setAction(dto.getAction().toUpperCase());
        log.setComment(dto.getComment());
        log.setActionByUser(approver);

        requestLogRepository.save(log);

        return updatedRequest;
    }
    public java.util.List<Request> getRequestsForUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return requestRepository.findByUser(user);
    }
    public java.util.List<Request> getPendingRequestsForRole(String roleName) {
        return requestRepository.findByCurrentApproverRole(roleName);
    }
}