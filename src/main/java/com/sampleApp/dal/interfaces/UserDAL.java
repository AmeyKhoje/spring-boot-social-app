package com.sampleApp.dal.interfaces;

import com.sampleApp.auth.AuthenticationRequest;
import com.sampleApp.auth.AuthenticationResponse;
import com.sampleApp.auth.RegisterRequest;
import com.sampleApp.models.User;

import java.util.List;
import java.util.Optional;

public interface UserDAL {

  List<User> getAllUsers();

  Object getUserById(String id);

  Optional<User> findByEmail(String email);

  AuthenticationResponse create(RegisterRequest user);
}
