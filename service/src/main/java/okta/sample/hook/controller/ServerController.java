package okta.sample.hook.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/service")
public class ServerController {

  @GetMapping
  public String provideSerice(){
    SecurityContextHolder.getContext().getAuthentication().getPrincipal()
    return "You have the grant so I will make it for you";
  }
}
