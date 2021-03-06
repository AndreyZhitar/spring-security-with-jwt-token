package com.zhitar.jwttoken.rest;

import com.zhitar.jwttoken.dto.AdminUserDto;
import com.zhitar.jwttoken.model.User;
import com.zhitar.jwttoken.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/")
public class AdminRestController {

    private final UserService userService;

    public AdminRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("users/{id}")
    public ResponseEntity<AdminUserDto> getUserByID(@PathVariable Long id) {
        User user = userService.findById(id);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        AdminUserDto userDto = AdminUserDto.fromUser(user);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }
}
