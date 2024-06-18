package okta.sample.hook.config;

import java.util.Collection;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;

public class UserRoleAuthoritiyConverter implements Converter<Jwt, Collection<GrantedAuthority>> {
  JwtGrantedAuthoritiesConverter defaultConverter = new JwtGrantedAuthoritiesConverter();

  @Override
  public Collection<GrantedAuthority> convert(Jwt jwt) {
    Collection<GrantedAuthority> defaults = defaultConverter.convert(jwt);
    defaults.add(new SimpleGrantedAuthority("ROLE_USER"));
    return defaults;
  }
}
