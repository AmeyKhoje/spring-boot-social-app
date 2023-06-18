package com.sampleApp.utils;

import com.sampleApp.models.Product;
import com.sampleApp.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

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

  public Object getDocumentEntry(Class classObject, String id, String key) {
    Query query = new Query();
    query.addCriteria(Criteria.where(key).is(id));
    Object item = mongoTemplate.findOne(query, classObject);
    return item;
  }
}
