package com.example.demo.controllers;

import com.example.demo.dto.AuthResponseDto;
import com.example.demo.dto.LoginDto;
import com.example.demo.models.RoleEntity;
import com.example.demo.models.UserEntity;
import com.example.demo.repositories.RoleRepository;
import com.example.demo.security.JWTGenerator;
import com.example.demo.services.UserService;
import io.micrometer.common.lang.NonNull;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
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

  @Autowired private RoleRepository roleRepository; // TODO: replace by role service

  @PostMapping("/login")
  public ResponseEntity<AuthResponseDto> login(@RequestBody LoginDto loginDto) {
    logger.info("Login request: {}", loginDto);
    try {
      UserDetails userDetails = userService.loadUserByUsername(loginDto.getUsername());
      logger.info("User details: {}", userDetails);
      if (!userService.matchesPassword(loginDto.getPassword(), userDetails.getPassword())) {
        throw new BadCredentialsException("Invalid username or password");
      }
      logger.info("Login successful");

      // Authentication authentication = authenticationManager.authenticate(
      //         new UsernamePasswordAuthenticationToken(
      //                 userDetails.getUsername(),
      //                 userDetails.getPassword(),
      //                 userDetails.getAuthorities()));
      // SecurityContextHolder.getContext().setAuthentication(authentication);

      String token = jwtGenerator.generateToken(userDetails);
      // String token = "1234";
      return new ResponseEntity<>(new AuthResponseDto(token), HttpStatus.OK);

    } catch (Exception e) {
      logger.error(null, e);
      return new ResponseEntity<>(new AuthResponseDto(""), HttpStatus.UNAUTHORIZED);
    }
  }

  @PostMapping("/register")
  public ResponseEntity<String> Register(@RequestBody @NonNull LoginDto loginDto) {

    UserEntity user = new UserEntity(loginDto.getUsername(), loginDto.getPassword());
    RoleEntity role = roleRepository.findByName("USER").get();
    user.setRoles(List.of(role));

    userService.createUser(user);

    return new ResponseEntity<>("User Registered", HttpStatus.OK);
  }
}
