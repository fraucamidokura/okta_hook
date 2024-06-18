package okta.sample.hook.payment;

import java.time.LocalDateTime;

public record Payment(int product, String user, LocalDateTime expiration) {

}
