package okta.sample.hook.proxy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ProxyService {
  Logger log = LoggerFactory.getLogger(ProxyService.class);

  public RestTemplate service;
  public RestTemplate payment;
  public ProxyService(
      @Value("${proxy.service_url}") String serviceUrl,
      @Value("${proxy.payment_url}") String paymentUrl){
    this.service = template(serviceUrl);
    this.payment = template(paymentUrl);
  }

  public OidcIdToken getToken(){
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    if(auth instanceof OAuth2AuthenticationToken jwt
        && jwt.getPrincipal() instanceof DefaultOidcUser user){
      return user.getIdToken();
    }
    return OidcIdToken.withTokenValue("token.not.found").build();
  }
  private RestTemplate template(String url){
    return new RestTemplateBuilder()
        .rootUri(url)
        .additionalInterceptors(((request, body, execution) -> {
          var token = this.getToken().getTokenValue();
          log.info("Adding token {} to request",token);
          request.getHeaders().add(HttpHeaders.AUTHORIZATION,"Bearer "+token);
          return  execution.execute(request, body);
        }))
        .build();
  }
}
