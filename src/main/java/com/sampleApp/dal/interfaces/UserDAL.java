package com.sampleApp.dal.interfaces;

import com.sampleApp.models.User;

import java.util.List;
import java.util.Optional;

public interface UserDAL {

  List<User> getAllUsers();

  Object getUserById(String id);

  User create(User user);
}
