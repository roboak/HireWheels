package com.upgrad.hirewheels.security.jwt;

import com.upgrad.hirewheels.exceptions.UserNotFoundException;
import com.upgrad.hirewheels.service.UserServiceImpl;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;


@Component
public class JwtTokenProvider {

  @Value("${security.jwt.token.secret-key}")
  private String secretKey;

 // @Value("${security.jwt.token.expire-length}")
  private long validityInMilliseconds = 600000;//10 minutes

  //@Value("${security.jwt.refresh.expire-length}")
  private long refreshValidityInMilliseconds=3600000;

  @Autowired
  UserServiceImpl userService;

  @PostConstruct
  protected void init() {
    secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
  }

  public String createToken(String emailId) {

    Claims claims = Jwts.claims().setSubject(emailId);

    Date now = new Date();
    Date validity = new Date(now.getTime() + validityInMilliseconds);

    return Jwts.builder()
        .setClaims(claims)
        .setIssuedAt(now)
        .setExpiration(validity)
        .signWith(SignatureAlgorithm.HS256, secretKey)
        .compact();
  }


  public Authentication getAuthentication(String token) throws UserNotFoundException {
    UserDetails userDetails = this.userService.loadUserDetails(getEmail(token));
    return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
  }

  public String getEmail(String token) {
    return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
  }

  public String resolveToken(HttpServletRequest req) {
    String bearerToken = req.getHeader("X-Access-Token");
    if (bearerToken != null) {
      return bearerToken.trim();
    }
    return null;
  }

  public boolean validateToken(String token) throws InvalidJwtAuthenticationException {

    try {
      Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);

      if (claims.getBody().getExpiration().before(new Date())) {
        return false;
      }
      return true;
    } catch (JwtException | IllegalArgumentException e) {
      throw new InvalidJwtAuthenticationException("Expired or invalid JWT token");
    }
  }
}
