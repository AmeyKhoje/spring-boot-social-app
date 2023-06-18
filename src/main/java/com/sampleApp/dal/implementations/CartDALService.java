package com.sampleApp.dal.implementations;

import com.sampleApp.dal.interfaces.CartDAL;
import com.sampleApp.models.Cart;
import com.sampleApp.models.internals.CartItem;
import com.sampleApp.utils.DatabaseUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Queue;

@Repository
public class CartDALService implements CartDAL {

  @Autowired
  private MongoTemplate mongoTemplate;

  @Autowired
  private DatabaseUtility databaseUtility;

  @Override
  public Cart modifyCart(CartItem cartItem, String userId) {
    Boolean isCartAlreadyExists = databaseUtility.checkIfEntryExistsById(Cart.class, userId, "userId");
    if (isCartAlreadyExists) {
      Cart existingCart = (Cart) databaseUtility.getDocumentEntry(Cart.class, userId, "userId");
      if (!existingCart.getCartId().isEmpty()) {
        Collection<CartItem> modifiedCart = new ArrayList<>();
        boolean isItemExist = existingCart.getItems().stream().filter(item -> item.getProductId() == cartItem.getProductId()).count() > 0;

        if (isItemExist) {
          existingCart.getItems().stream().forEach(item -> {
            if (item.getProductId() == cartItem.getProductId()) {
              modifiedCart.add(cartItem);
            }
            else {
              modifiedCart.add(item);
            }
          });
        }
        else {
          modifiedCart.addAll(existingCart.getItems());
          modifiedCart.add(cartItem);
        }

        Query query = new Query();
        query.addCriteria(Criteria.where("userId").is(userId));
        Update update = new Update();
        update.set("cartItems", modifiedCart);

        return mongoTemplate
                .findAndModify(
                        query,
                        update,
                        FindAndModifyOptions.options().returnNew(true),
                        Cart.class
                );
      }
      else {
        return null;
      }

    }
    else {
      return createNewCart(cartItem, userId);
    }
  }

  @Override
  public Cart getCartOfUser(String userId) {
    Boolean isCartAlreadyExists = databaseUtility.checkIfEntryExistsById(Cart.class, userId, "userId");
    if (isCartAlreadyExists) {
      Query query = new Query();
      query.addCriteria(Criteria.where("userId").is(userId));
      return mongoTemplate.findOne(query, Cart.class);
    }
    else {
      return null;
    }
  }

  private Cart createNewCart(CartItem cartItem, String userId) {
    try {
      Collection<CartItem> cartItems = new ArrayList<>();
      cartItems.add(cartItem);
      Cart newCart = Cart.builder().userId(userId).items(cartItems).build();
      return mongoTemplate.save(newCart);
    }
    catch (Exception e) {
      throw e;
    }
  }
}
