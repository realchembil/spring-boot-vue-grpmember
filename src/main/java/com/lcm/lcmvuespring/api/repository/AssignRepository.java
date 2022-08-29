package com.lcm.lcmvuespring.api.repository;

import com.lcm.lcmvuespring.api.model.Assign;

import com.lcm.lcmvuespring.api.model.AssignStatus;
import com.lcm.lcmvuespring.api.model.SecurityGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AssignRepository extends JpaRepository<Assign,Long> {
    Optional<Assign> findByGroupAndAssignstatus(SecurityGroup securityGroup, AssignStatus assignStatusVal);
}
