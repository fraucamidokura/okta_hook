package okta.sample.hook.controller;

import okta.sample.hook.payment.PaymentService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("payment")
public class PaymentController {
  private PaymentService payment;

  public PaymentController(PaymentService payment) {
    this.payment = payment;
  }


  @GetMapping
  public String oneMinute(@RequestParam(required = true) int product){
   payment.pay(product,getUser());
   return "you have one more minute to access the service";
  }

  private String getUser(){
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    if (auth.getPrincipal() instanceof Jwt jwt){
      return jwt.getClaimAsString("name");
    }
    return auth.getName();
  }
}
