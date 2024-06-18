package okta.sample.hook.payment;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {
  private List<Payment> payments = new ArrayList<>() ;

  public synchronized void pay(int product,String user){
    Optional<Payment> current = payments.stream().filter(p->p.user().equals(user)).findAny();
    LocalDateTime expiration = LocalDateTime.now();
    if (current.isPresent() && current.get().expiration().isAfter(LocalDateTime.now())){
      expiration = current.get().expiration();
      payments.remove(current.get());
    }
    payments.add(new Payment(product,user, expiration.plusMinutes(1)));
  }

  public LocalDateTime getExpiration(int product,String user){
    return payments.stream().filter(p->p.user().equals(user) && p.product() == product)
        .map(p->p.expiration())
        .findAny().orElse(LocalDateTime.now().minusMinutes(1));
  }
}
