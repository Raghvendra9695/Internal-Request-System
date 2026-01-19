package com.project.internal_request_system.repository;

import com.project.internal_request_system.entity.RequestType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RequestTypeRepository extends JpaRepository<RequestType, Long> {
    RequestType findByName(String name);
}