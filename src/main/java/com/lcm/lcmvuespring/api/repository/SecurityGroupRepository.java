package com.lcm.lcmvuespring.api.repository;

import com.lcm.lcmvuespring.api.model.SecurityGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SecurityGroupRepository extends JpaRepository<SecurityGroup,Long> {
    Optional<SecurityGroup> findBySamAccountName(String samAccountName);
}
