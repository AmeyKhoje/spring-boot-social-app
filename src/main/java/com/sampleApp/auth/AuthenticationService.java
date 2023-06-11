package com.sampleApp.auth;

import com.sampleApp.auth.token.Token;
import com.sampleApp.auth.token.TokenType;
import com.sampleApp.config.JwtService;
import com.sampleApp.dal.implementations.TokenDALService;
import com.sampleApp.dal.implementations.UserDALService;
import com.sampleApp.models.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

  private final UserDALService userDALService;
  private final TokenDALService tokenDALService;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;

//  public AuthenticationResponse register(User user) {
//    User registeredUser = userDALService.create(user);
//    var jwtToken = jwtService.generateToken((UserDetails) registeredUser);
//    var refreshToken = jwtService.generateRefreshToken((UserDetails) registeredUser);
//    saveUserToken(registeredUser, jwtToken);
//
//    return AuthenticationResponse
//            .builder()
//            .accessToken(jwtToken)
//            .refreshToken(refreshToken)
//            .build();
//  }

  public AuthenticationResponse authenticate(AuthenticationRequest request) {
    System.out.println("jdhvsd");
    final Collection<GrantedAuthority> authoritySet = new HashSet<GrantedAuthority>();

    try {
      authenticationManager.authenticate(
              new UsernamePasswordAuthenticationToken(
                      request.getEmail(),
                      request.getPassword(),
                      authoritySet
              )
      );
    }
    catch (Exception e) {
      System.out.println("Here Ex "+e);
//      throw new Exception(e);
    }
    System.out.println("Here1");

    User user = userDALService.findByEmail(request.getEmail()).orElseThrow();
    var jwtToken = jwtService.generateToken((UserDetails) user);
    var refreshToken = jwtService.generateRefreshToken((UserDetails) user);

    revokeAllUserTokens(user);
    saveUserToken(user, jwtToken);

    return AuthenticationResponse
            .builder()
            .accessToken(jwtToken)
            .refreshToken(refreshToken)
            .build();

  }

  private void saveUserToken(User user, String jwtToken) {
    var token = Token
            .builder()
            .user(user)
            .token(jwtToken)
            .tokenType(TokenType.BEARER)
            .expired(false)
            .revoked(false)
            .build();
    tokenDALService.create(token);
  }

  private void revokeAllUserTokens(User user) {
    var validUserTokens = tokenDALService.findAllValidTokenByUser(user.getUserId());
    if (validUserTokens.isEmpty()) {
      return;
    }
    tokenDALService.updateAllToExpiry(user.getUserId());
  }
}
