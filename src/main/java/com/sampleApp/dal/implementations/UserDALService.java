package com.sampleApp.dal.implementations;

import com.sampleApp.dal.interfaces.UserDAL;
import com.sampleApp.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UserDALService implements UserDAL {

  @Autowired
  private MongoTemplate mongoTemplate;

  private final PasswordEncoder passwordEncoder;

  public UserDALService(PasswordEncoder passwordEncoder) {
    this.passwordEncoder = passwordEncoder;
  }

  @Override
  public List<User> getAllUsers() {
    return mongoTemplate.findAll(User.class);
  }

  @Override
  public Object getUserById(String id) {
    Query query = new Query();
    query.addCriteria(Criteria.where("userId").is(id));
    Optional<User> user = Optional.ofNullable(mongoTemplate.findOne(query, User.class));
    if (!user.isEmpty()) {
      return user;
    }
    else {
      return "Not found";
    }
  }

  @Override
  public Optional<User> findByEmail(String emailId) {
    Query query = new Query();
    query.addCriteria(Criteria.where("email").is(emailId));
    Optional<User> user = Optional.ofNullable(mongoTemplate.findOne(query, User.class));
    if (!user.isEmpty()) {
      return user;
    }
    else return null;
  }

  @Override
  public User create(User user) {
    User newUser = User.builder()
                    .firstName(user.getFirstName())
                    .lastName(user.getLastName())
                    .email(user.getEmail())
                    .mobile(user.getMobile())
                    .password(passwordEncoder.encode(user.getPassword()))
                    .city(user.getCity())
                    .build();
    mongoTemplate.save(newUser);
    return user; // Object will contain id as well automatically
  }
}
