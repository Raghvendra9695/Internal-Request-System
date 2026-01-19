package com.project.internal_request_system.repository;

import com.project.internal_request_system.entity.RequestLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface RequestLogRepository extends JpaRepository<RequestLog, Long> {
    List<RequestLog> findByRequestIdOrderByActionDateDesc(Long requestId);
}