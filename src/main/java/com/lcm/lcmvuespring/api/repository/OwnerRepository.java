package com.lcm.lcmvuespring.api.repository;

import com.lcm.lcmvuespring.api.model.Owner;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OwnerRepository extends JpaRepository<Owner,Long> {
}
