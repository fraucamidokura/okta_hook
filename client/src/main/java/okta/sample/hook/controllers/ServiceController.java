package okta.sample.hook.controllers;

import okta.sample.hook.proxy.ProxyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/proxy")
public class ServiceController {
  Logger log = LoggerFactory.getLogger(ServiceController.class);
  private ProxyService proxy;

  public ServiceController(ProxyService proxy) {
    this.proxy = proxy;
  }

  @GetMapping("/service")
  public ResponseEntity<String> getService(@RequestParam int product){
    log.debug("The token is {}",proxy.getToken().getTokenValue());
    return proxy.service.getForEntity("/service?product="+product, String.class);
  }

  @GetMapping("/payment")
  public ResponseEntity<String> pay(@RequestParam int product){
    log.debug("The token is {}",proxy.getToken().getTokenValue());
    return proxy.payment.getForEntity("/payment?product="+product, String.class);
  }
}
