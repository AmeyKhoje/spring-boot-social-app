package com.sampleApp.dal.implementations;

import com.sampleApp.auth.AuthenticationRequest;
import com.sampleApp.auth.AuthenticationResponse;
import com.sampleApp.auth.RegisterRequest;
import com.sampleApp.dal.interfaces.UserDAL;
import com.sampleApp.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Repository
public class UserDALService implements UserDAL {

  @Autowired
  private MongoTemplate mongoTemplate;

  private final PasswordEncoder passwordEncoder;

  public UserDALService(@Lazy PasswordEncoder passwordEncoder) {
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
  public AuthenticationResponse create(RegisterRequest request) {
    System.out.println(request.getEmail());
    User newUser = User.builder()
                    .firstName(request.getFirstname())
                    .lastName(request.getLastname())
                    .email(request.getEmail())
                    .mobile(request.getMobile())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .city(request.getCity())
                    .build();
    mongoTemplate.save(newUser);
    return AuthenticationResponse.builder().build(); // Object will contain id as well automatically
  }
}
