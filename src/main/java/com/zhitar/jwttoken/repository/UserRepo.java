package com.zhitar.jwttoken.repository;

import com.zhitar.jwttoken.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Long> {

    User findByUsername(String name);
}
