package okta.sample.hook.config;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class OktaHeaderFilter extends OncePerRequestFilter {
  private final String headerName;
  private final String headerKey;

  public OktaHeaderFilter(
      @Value("${okta-hook.header}") String headerName,
      @Value("${okta-hook.password}") String headerKey) {
    this.headerName = headerName;
    this.headerKey = headerKey;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {
    String password = request.getHeader(headerName);
    if( authenticationPending() && oktaHookCall(password)){
      var context = SecurityContextHolder.createEmptyContext();
      context.setAuthentication(new PreAuthenticatedAuthenticationToken(
          "okta", null, List.of(new SimpleGrantedAuthority("ROLE_OKTA-HOOK"))));
      SecurityContextHolder.setContext(context);
    }
    filterChain.doFilter(request, response);
  }

  private boolean oktaHookCall(String password){
    return password!=null && password.equals(headerKey);
  }

  private boolean authenticationPending(){
    return SecurityContextHolder.getContext().getAuthentication() == null
        || !SecurityContextHolder.getContext().getAuthentication().isAuthenticated();
  }
}
