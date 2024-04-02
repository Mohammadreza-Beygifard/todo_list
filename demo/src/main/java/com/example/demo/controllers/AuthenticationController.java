package com.example.demo.controllers;

import com.example.demo.dto.AuthResponseDto;
import com.example.demo.dto.LoginDto;
import com.example.demo.models.RoleEntity;
import com.example.demo.models.UserEntity;
import com.example.demo.security.JWTGenerator;
import com.example.demo.services.RoleService;
import com.example.demo.services.UserService;
import io.micrometer.common.lang.NonNull;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("api/v1/auth")
public class AuthenticationController {

  Logger logger = LoggerFactory.getLogger(AuthenticationController.class);

  @Autowired private UserService userService;
  @Autowired private AuthenticationManager authenticationManager;
  @Autowired private JWTGenerator jwtGenerator;

  @Autowired private RoleService roleService;

  @PostMapping("/login")
  public ResponseEntity<AuthResponseDto> login(@RequestBody LoginDto loginDto) {
    logger.info("Login request: {}", loginDto);
    try {

      Authentication authentication =
          authenticationManager.authenticate(
              new UsernamePasswordAuthenticationToken(
                  loginDto.getUsername(), loginDto.getPassword()));
      SecurityContextHolder.getContext().setAuthentication(authentication);

      String token = jwtGenerator.generateToken(authentication);
      logger.info("Login successful");
      return new ResponseEntity<>(new AuthResponseDto(token), HttpStatus.OK);

    } catch (Exception e) {
      logger.error(null, e);
      return new ResponseEntity<>(new AuthResponseDto(""), HttpStatus.UNAUTHORIZED);
    }
  }

  @PostMapping("/register")
  public ResponseEntity<String> Register(@RequestBody @NonNull LoginDto loginDto) {

    UserEntity user = new UserEntity(loginDto.getUsername(), loginDto.getPassword());
    RoleEntity role = roleService.findByName("USER");
    user.setRoles(List.of(role));

    userService.createUser(user);

    return new ResponseEntity<>("User Registered", HttpStatus.OK);
  }
}
