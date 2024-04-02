package com.example.demo.services;

import com.example.demo.models.RoleEntity;
import com.example.demo.models.UserEntity;
import com.example.demo.repositories.UserRepository;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

  Logger logger = LoggerFactory.getLogger(UserService.class);

  @Autowired private UserRepository userRepository;

  @Autowired private PasswordEncoder passwordEncoder;

  public void createUser(@NonNull UserEntity user) {
    if (userRepository.findByUsername(user.getUsername()).isPresent()) {
      throw new RuntimeException("User already exists");
    }
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    userRepository.save(user);
  }

  public List<UserEntity> getAllUsers() {

    return userRepository.findAll();
  }

  public void deleteUser(@NonNull UserEntity user) {

    userRepository.delete(user);
  }

  public boolean matchesPassword(String rawPassword, String encodedPassword) {
    return passwordEncoder.matches(rawPassword, encodedPassword);
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    logger.info("Load user by username: {}", username);
    UserEntity user =
        userRepository
            .findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    logger.info("User found: {}", user);
    List<RoleEntity> roles = user.getRoles();
    for (RoleEntity role : roles) {
      logger.info("Role: {}", role.getName());
    }
    User user_ = new User(user.getUsername(), user.getPassword(), mapRolesToAuthorities(roles));
    logger.info("User: {}", user_.getClass().getName());
    return user_;
  }

  private Collection<GrantedAuthority> mapRolesToAuthorities(List<RoleEntity> roles) {

    return roles.stream()
        .map((RoleEntity role) -> new SimpleGrantedAuthority(role.getName()))
        .collect(Collectors.toList());
  }
}
