package com.project.internal_request_system.service;

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

        // Logic: By default pehle HOD ke paas jayega
        request.setCurrentApproverRole("ROLE_HOD");

        Request savedRequest = requestRepository.save(request);

        // 4. Audit Log Create karo (History)
        RequestLog log = new RequestLog();
        log.setRequest(savedRequest);
        log.setAction("CREATED");
        log.setComment("Request initiated by user.");
        log.setActionByUser(user); // Khud user ne action liya hai

        requestLogRepository.save(log);

        return savedRequest;
    }
}