package com.zhitar.jwttoken.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.zhitar.jwttoken.model.Status;
import com.zhitar.jwttoken.model.User;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AdminUserDto {

    private Long id;
    private String username;
    private String firstname;
    private String lastname;
    private String email;
    private String status;

    public User toUser() {
        User user = new User();
        user.setId(id);
        user.setUsername(username);
        user.setFirstname(firstname);
        user.setLastname(lastname);
        user.setEmail(email);
        user.setStatus(Status.valueOf(status));
        return user;
    }

    public static AdminUserDto fromUser(User user) {
        AdminUserDto userDto = new AdminUserDto();
        userDto.setId(user.getId());
        userDto.setUsername(user.getUsername());
        userDto.setFirstname(user.getFirstname());
        userDto.setLastname(user.getLastname());
        userDto.setEmail(user.getEmail());
        userDto.setStatus(user.getStatus().name());
        return userDto;
    }
}
