package okta.sample.hook.controller;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
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

  public static List<Integer> aviableProducts(){
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    if (auth.getPrincipal() instanceof Jwt jwt){

      List<Map<String,Object>> payments = getPayments(jwt);
      return payments.stream().flatMap(AuthUtils::filerAndMapPayments).toList();
    }
    return Collections.emptyList();
  }

  public static List<Map<String,Object>> getPayments(Jwt jwt){
    if(jwt.hasClaim("payment")){
      Map<String,List<Map<String,Object>>> payments = jwt.getClaim("payment");
      return payments.getOrDefault("payments",Collections.emptyList());
    }
    return Collections.emptyList();
  }

  public static Stream<Integer> filerAndMapPayments(Map<String,Object> payment){
    String expirationRaw = payment.get("expiration").toString();
    LocalDateTime expiration = LocalDateTime.parse(expirationRaw);
    if(expiration.isBefore(LocalDateTime.now())){
      return Stream.empty();
    }
    Long product = (Long)payment.get("product");
    return Stream.of(product.intValue());
  }
}
