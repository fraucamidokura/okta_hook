package okta.sample.hook.controller;

import java.util.List;
import org.springframework.http.HttpStatusCode;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpStatusCodeException;

@RestController
@RequestMapping("/service")
public class ServerController {

  @GetMapping
  public String provideSerice(@RequestParam int product){
    List<Integer>aviableProducts = AuthUtils.aviableProducts();
    if (!aviableProducts.contains(product)){
      throw new HttpClientErrorException(HttpStatusCode.valueOf(403),"Please purchase product  "+product);
    }
    return "You have the grant so I will make it for you";
  }

}
