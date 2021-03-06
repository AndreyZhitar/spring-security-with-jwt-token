package com.zhitar.jwttoken.service.impl;

import com.zhitar.jwttoken.model.Role;
import com.zhitar.jwttoken.model.Status;
import com.zhitar.jwttoken.model.User;
import com.zhitar.jwttoken.repository.RoleRepo;
import com.zhitar.jwttoken.repository.UserRepo;
import com.zhitar.jwttoken.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;
    private final RoleRepo roleRepo;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepo userRepo, RoleRepo roleRepo, BCryptPasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User register(User user) {
        Role roleUser = roleRepo.findByName("ROLE_USER");
        List<Role> roles = new ArrayList<>();
        roles.add(roleUser);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setStatus(Status.ACTIVE);
        User registeredUser = userRepo.save(user);
        log.info("In register - user: {} successfully registered", registeredUser);

        return registeredUser;
    }

    @Override
    public List<User> getAll() {
        List<User> userList = userRepo.findAll();
        log.info("In getAll - {} users found", userList.size());
        return userList;
    }

    @Override
    public User findByUsername(String username) {
        User user = userRepo.findByUsername(username);
        log.info("In findByUsername - user: {} found by username: {}", user, username);
        return user;
    }

    @Override
    public User findById(Long id) {
        User user = userRepo.findById(id).orElse(null);
        log.info("In findById - user: {} found by id: {}", user, id);
        return user;
    }

    @Override
    public void delete(Long id) {
        userRepo.deleteById(id);
        log.info("In delete - user with id: {} successfully deleted", id);
    }
}
