package okta.sample.hook.controller;

import com.fasterxml.jackson.databind.JsonNode;
import java.util.List;
import java.util.Map;
import okta.sample.hook.payment.PaymentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/okta-token-hook")
public class OktaHookClaimController {

  Logger logger = LoggerFactory.getLogger(OktaHookClaimController.class);
  private PaymentService payment;

  public OktaHookClaimController(PaymentService payment) {
    this.payment = payment;
  }

  @PostMapping( produces = MediaType.APPLICATION_JSON_VALUE)
  public Map<String, Object> getClaims(@RequestBody JsonNode oktaInfo) {
    logger.info("Claims has been requested");
    String user = oktaInfo.path("data").path("access").path("claims").path("sub").toString();
    return Map.of("commands", List.of(
        Map.of("type", "com.okta.identity.patch",
        "value", List.of(
            Map.of("op", "add",
            "path", "/claims/custom_roger",
            "value", "simple test"),
            Map.of("op", "add",
                "path", "/claims/roger_claim",
                "value", "simple test")))));
  }
}
