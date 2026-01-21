package com.project.internal_request_system.service;

import com.project.internal_request_system.dto.UserRegistrationDto;
import com.project.internal_request_system.entity.Department;
import com.project.internal_request_system.entity.Role;
import com.project.internal_request_system.entity.User;
import com.project.internal_request_system.repository.DepartmentRepository;
import com.project.internal_request_system.repository.RoleRepository;
import com.project.internal_request_system.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private RoleRepository roleRepository;
    public User registerUser(UserRegistrationDto dto) {

        if (userRepository.existsByUsername(dto.getUsername())) {
            throw new RuntimeException("Username already exists!");
        }
        Department dept = departmentRepository.findByName(dto.getDepartmentName());
        if (dept == null) {
            throw new RuntimeException("Department not found: " + dto.getDepartmentName());
        }
        Role role = roleRepository.findByName(dto.getRoleName());
        if (role == null) {
            throw new RuntimeException("Role not found: " + dto.getRoleName());
        }

        User newUser = new User();
        newUser.setUsername(dto.getUsername());
        newUser.setEmail(dto.getEmail());
        newUser.setFullName(dto.getFullName());

        newUser.setPasswordHash(dto.getPassword());
        newUser.setDepartment(dept);

        Set<Role> roles = new HashSet<>();
        roles.add(role);
        newUser.setRoles(roles);
        return userRepository.save(newUser);
    }
}