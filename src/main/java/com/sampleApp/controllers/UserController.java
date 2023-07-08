package com.sampleApp.controllers;

import com.sampleApp.auth.AuthenticationRequest;
import com.sampleApp.auth.AuthenticationResponse;
import com.sampleApp.auth.RegisterRequest;
import com.sampleApp.dal.implementations.UserDALService;
import com.sampleApp.dal.interfaces.UserDAL;
import com.sampleApp.models.User;
import com.sampleApp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Component
@RestController
@RequestMapping("/users")
public class UserController {

  private final UserDAL userDALService;
  private final UserRepository userRepository;

  @Autowired
  public UserController(UserRepository userRepository, UserDAL userDAL) {
    this.userRepository = userRepository;
    this.userDALService = userDAL;
  }

//  @PreAuthorize("hasAuthority('Admin')")
  @PostMapping("/_new")
  public ResponseEntity<AuthenticationResponse> create(@RequestBody RegisterRequest request) {
    return ResponseEntity.ok(userDALService.create(request));
  }

  @GetMapping("/_all")
  public List<User> getAllUsers() {
    System.out.println("here");
    return userDALService.getAllUsers();
  }

  @GetMapping("/{userId}")
  public Object getUserById(@PathVariable String userId) {
    return userDALService.getUserById(userId);

  }
}