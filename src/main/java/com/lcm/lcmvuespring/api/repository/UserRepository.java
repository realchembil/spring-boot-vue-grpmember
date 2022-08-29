package com.lcm.lcmvuespring.api.repository;

import com.lcm.lcmvuespring.api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
}
