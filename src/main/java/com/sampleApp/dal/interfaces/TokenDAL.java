package com.sampleApp.dal.interfaces;

import com.sampleApp.auth.token.Token;

import java.util.List;
import java.util.Optional;

public interface TokenDAL {
  List<Token> findAllValidTokenByUser(String id);
  Optional<Token> findByToken(String token);

  Object create(Token token);
}
