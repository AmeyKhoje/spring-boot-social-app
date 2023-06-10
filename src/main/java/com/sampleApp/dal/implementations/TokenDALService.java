package com.sampleApp.dal.implementations;

import com.mongodb.client.result.UpdateResult;
import com.sampleApp.auth.token.Token;
import com.sampleApp.dal.interfaces.TokenDAL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

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

  @Override
  public Token create(Token token) {
    return mongoTemplate.save(token);
  }

  public UpdateResult updateAllToExpiry(String userId) {
    Query query = new Query();
    query.addCriteria(Criteria.where("userId").is(userId));
    Update update = new Update();
    update.set("expired", true);
    update.set("revoked", true);
    return mongoTemplate.updateMulti(query, update, Token.class);
  }
}
