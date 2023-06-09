package com.sampleApp.controllers;

import com.sampleApp.dal.implementations.UserDALService;
import com.sampleApp.dal.interfaces.UserDAL;
import com.sampleApp.models.User;
import com.sampleApp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

  @PostMapping("/_new")
  public User create(User user) {
    return userDALService.create(user);
  }

  @GetMapping("/_all")
  public List<User> getAllUsers() {
    return userDALService.getAllUsers();
  }

  @GetMapping("/{userId}")
  public Object getUserById(@PathVariable String userId) {
    return userDALService.getUserById(userId);

  }
}