package com.zhitar.jwttoken.rest;

import com.zhitar.jwttoken.dto.AuthenticationRequestDto;
import com.zhitar.jwttoken.model.User;
import com.zhitar.jwttoken.security.jwt.JwtTokenProvider;
import com.zhitar.jwttoken.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/auth/")
public class AuthenticationRestController {

    private final AuthenticationManager manager;
    private final JwtTokenProvider tokenProvider;
    private final UserService userService;

    @Autowired
    public AuthenticationRestController(AuthenticationManager manager, JwtTokenProvider tokenProvider, UserService userService) {
        this.manager = manager;
        this.tokenProvider = tokenProvider;
        this.userService = userService;
    }

    @PostMapping("login")
    public ResponseEntity login(@RequestBody AuthenticationRequestDto requestDto) {
        try {
            String username = requestDto.getUsername();
            manager.authenticate(new UsernamePasswordAuthenticationToken(username, requestDto.getPassword()));
            User user = userService.findByUsername(username);
            if (user == null) {
                throw new UsernameNotFoundException("User with username " + username + " not found");
            }
            String token = tokenProvider.createToken(username, user.getRoles());
            Map<String, String> response = new HashMap<>();
//            response.put("username", username);
            response.put("token", token);
            return ResponseEntity.ok(response);
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid username or password");
        }
    }
}
