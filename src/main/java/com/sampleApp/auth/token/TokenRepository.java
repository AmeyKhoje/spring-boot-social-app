package com.sampleApp.auth.token;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends MongoRepository {

  List<Token> findAllValidTokenByUser(String id);
  Optional<Token> findByToken(String token);
}
