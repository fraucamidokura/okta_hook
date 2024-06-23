package okta.sample.hook.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;

public class AuthUtils {

  public static String getUser(){
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    if (auth.getPrincipal() instanceof Jwt jwt){
      String mail = jwt.getClaimAsString("email");
      return mail;
    }
    return auth.getName();
  }
}
