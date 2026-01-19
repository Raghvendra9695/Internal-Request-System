package com.project.internal_request_system.repository;

import com.project.internal_request_system.entity.Request;
import com.project.internal_request_system.entity.RequestStatus;
import com.project.internal_request_system.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface RequestRepository extends JpaRepository<Request, Long> {

    List<Request> findByUser(User user);
    List<Request> findByStatus(RequestStatus status);

    List<Request> findByCurrentApproverRole(String roleName);
}