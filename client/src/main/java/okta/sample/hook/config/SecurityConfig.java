package okta.sample.hook.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtDecoders;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
  @Bean
  SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

    http.authorizeHttpRequests(autorize-> autorize
            .anyRequest().authenticated()
        )
        .oauth2Client(Customizer.withDefaults())
        .oauth2Login(Customizer.withDefaults())
        .cors(cors->cors.disable())
        .csrf(csrf->csrf.disable());

    return http.build();
  }
}
