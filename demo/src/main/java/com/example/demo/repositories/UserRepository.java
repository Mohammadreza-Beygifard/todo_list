package com.example.demo.repositories;

import com.example.demo.models.UserEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

  public Optional<UserEntity> findByUsername(String username);
}
