package com.sampleApp.models;

import com.sampleApp.models.internals.CartItem;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Collection;

@Data
@Document("carts")
@Builder
public class Cart {
  @Id
  private String cartId;
  private String userId;
  private Collection<CartItem> items;

  public Boolean isEmpty() {
    return userId == null && items.isEmpty();
  }

  public String getCartId() {
    return cartId;
  }

  public void setCartId(String cartId) {
    this.cartId = cartId;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public Collection<CartItem> getItems() {
    return items;
  }

  public void setItems(Collection<CartItem> items) {
    this.items = items;
  }
}
