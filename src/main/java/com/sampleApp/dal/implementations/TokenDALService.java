package com.sampleApp.dal.implementations;

import com.sampleApp.auth.token.Token;
import com.sampleApp.dal.interfaces.TokenDAL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;
import java.util.Optional;

public class TokenDALService implements TokenDAL {
  @Autowired
  private MongoTemplate mongoTemplate;

  @Override
  public List<Token> findAllValidTokenByUser(String id) {
    Query query = new Query();
    query.addCriteria(Criteria.where("userId").is(id));
    List<Token> tokens = mongoTemplate.find(query, Token.class);
    return tokens;
  }

  @Override
  public Optional<Token> findByToken(String token) {
    Query query = new Query();
    query.addCriteria(Criteria.where("token").is(token));
    Optional<Token> tokenItem = Optional.ofNullable(mongoTemplate.findOne(query, Token.class));
    return tokenItem;
  }
}
