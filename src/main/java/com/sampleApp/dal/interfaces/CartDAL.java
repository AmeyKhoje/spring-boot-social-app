package com.sampleApp.dal.interfaces;

import com.sampleApp.models.Cart;
import com.sampleApp.models.internals.CartItem;

public interface CartDAL {

  Cart modifyCart(CartItem cartItem, String userId);

  Cart getCartOfUser(String userId);

}
