package com.cyper.library.jwt;

import java.util.Date;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.crypto.SecretKey;
import javax.servlet.http.HttpServletRequest;

import com.cyper.library.exception.JwtFailureException;
import com.cyper.library.model.User;
import com.cyper.library.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JwtTokenProvider {
  @Value("${security.jwt.token.secret_key}")
  private String secret;

  @Value("${security.jwt.token.duration}")
  private long duration = 3600000;

  @Autowired
  @Lazy
  private UserService userService;

  private SecretKey secretKey;

  @PostConstruct
  protected void init() {
    secretKey = Keys.hmacShaKeyFor(secret.getBytes());
  }

  public String createToken(Map<String, Object> claims) {
    Date now = new Date();
    Date expireTime = new Date(now.getTime() + duration);
    log.debug("set expire time to " + expireTime.toString());

    return Jwts.builder()
        .setClaims(claims)
        .setExpiration(expireTime)
        .signWith(secretKey, SignatureAlgorithm.HS256)
        .compact();
  }

  public String createTokenByUsername(String username) {
    Claims claims = Jwts.claims().setSubject(username);
    return createToken(claims);
  }

  public String getUsername(String token) {
    return Jwts.parserBuilder().setSigningKey(secretKey).build()
        .parseClaimsJws(token).getBody().getSubject();
  }

  public Authentication getAuthentication(String token) {
    User userDetails = userService.loadUserByUsername(getUsername(token));
    return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
  }

  public Claims getClaims(String token) {
    Claims claims;
    try {
      claims = Jwts.parserBuilder().setSigningKey(secretKey).build()
          .parseClaimsJws(token)
          .getBody();
    } catch (Exception e) {
      claims = null;
    }
    return claims;
  }

  public String resolveToken(HttpServletRequest req) {
    String bearerToken = req.getHeader("Authorization");
    if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
      return bearerToken.substring(7);
    }
    return null;
  }

  public boolean validateToken(String token) {
    try {
      Jwts.parserBuilder().setSigningKey(secretKey).build()
          .parseClaimsJws(token);
      return true;
    } catch (JwtException | IllegalArgumentException e) {
      log.debug("token 失效：" + token);
      throw new JwtFailureException(token);
    }
  }

}
