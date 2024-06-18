package okta.sample.hook.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;

@Configuration
public class SecurityConfig {
  @Bean
  SecurityFilterChain filterChain(HttpSecurity http, OktaHeaderFilter oktaFilter) throws Exception {
    http
        .authorizeHttpRequests(authorize -> authorize
            .requestMatchers("/okta-token-hook").hasRole("OKTA-HOOK")
            .requestMatchers("/**").hasRole("USER")
            .anyRequest().authenticated()

        )
        .addFilterBefore(oktaFilter, AbstractPreAuthenticatedProcessingFilter.class)
        .oauth2ResourceServer((oauth2) -> oauth2.jwt(Customizer.withDefaults()))
        .cors(AbstractHttpConfigurer::disable)
        .csrf(AbstractHttpConfigurer::disable);
    return http.build();
  }

  @Bean
  JwtAuthenticationConverter jwtAuthenticationConverter(){
    JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
    converter.setJwtGrantedAuthoritiesConverter(new UserRoleAuthoritiyConverter());
    return converter;
  }
}
