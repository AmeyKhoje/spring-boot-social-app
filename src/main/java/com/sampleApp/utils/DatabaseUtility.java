package com.sampleApp.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public class DatabaseUtility {
  @Autowired
  private MongoTemplate mongoTemplate;

  public boolean checkIfEntryExistsById(Class classObject, String id, String key) {
    Query query = new Query();
    query.addCriteria(Criteria.where(key).is(id));
    boolean isExists = mongoTemplate.exists(query, classObject);
    return isExists;
  }
}
