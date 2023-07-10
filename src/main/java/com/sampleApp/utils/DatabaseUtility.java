package com.sampleApp.utils;

import com.sampleApp.config.JwtService;
import com.sampleApp.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class DatabaseUtility {
  @Autowired
  private MongoTemplate mongoTemplate;

  @Autowired
  private JwtService jwtService;

  public boolean checkIfEntryExistsById(Class classObject, String id, String key) {
    Query query = new Query();
    query.addCriteria(Criteria.where(key).is(id));
    boolean isExists = mongoTemplate.exists(query, classObject);
    return isExists;
  }

  public Object getDocumentEntry(Class classObject, String id, String key) {
    Query query = new Query();
    query.addCriteria(Criteria.where(key).is(id));
    Object item = mongoTemplate.findOne(query, classObject);
    return item;
  }
  public boolean checkSameUser(String userId, String authorizationToken) {
    String userEmail = jwtService.extractUsername(authorizationToken);
    User user = (User) getDocumentEntry(User.class, userId, "_id");
    String storedUserEmail = user.getEmail();

    if (userEmail != null && storedUserEmail != null) {
      return userEmail.matches(storedUserEmail);
    }

    return false;
  }
}
