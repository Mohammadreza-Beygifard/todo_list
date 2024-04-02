package com.example.demo.security;

import com.example.demo.services.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
public class JWTAuthenticationFilter extends OncePerRequestFilter {

  @Autowired private JWTGenerator tokenGenerator;
  @Autowired private UserService customUserDetailsService;

  // @Override
  // protected void doFilterInternal(HttpServletRequest request,
  // HttpServletResponse response,
  // FilterChain filterChain) throws ServletException, IOException {
  // String token = getJWTFromRequest(request);
  // if(StringUtils.hasText(token) && tokenGenerator.validateToken(token)) {
  // String username = tokenGenerator.getUsernameFromJWT(token);

  // UserDetails userDetails =
  // customUserDetailsService.loadUserByUsername(username);
  // UsernamePasswordAuthenticationToken authenticationToken = new
  // UsernamePasswordAuthenticationToken(userDetails, null,
  // userDetails.getAuthorities());
  // authenticationToken.setDetails(new
  // WebAuthenticationDetailsSource().buildDetails(request));
  // SecurityContextHolder.getContext().setAuthentication(authenticationToken);
  // }
  // filterChain.doFilter(request, response);
  // }

  private String getJWTFromRequest(HttpServletRequest request) {
    String bearerToken = request.getHeader("Authorization");
    log.info("getJWTFromRequest: {}", bearerToken);
    if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
      log.info("Entered here");
      return bearerToken.substring(7, bearerToken.length());
    }
    return null;
  }

  @Override
  protected void doFilterInternal(
      @NonNull HttpServletRequest request,
      @NonNull HttpServletResponse response,
      @NonNull FilterChain filterChain)
      throws jakarta.servlet.ServletException, IOException {

    log.info("doFilterInternal: {}", request.getRequestURI());
    log.info("doFilterInternal: {}", request.toString());
    String token = getJWTFromRequest(request);

    log.info("doFilterInternal: {}", token);
    if (StringUtils.hasText(token) && tokenGenerator.validateToken(token)) {
      String username = tokenGenerator.getUsernameFromJWT(token);

      UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
      UsernamePasswordAuthenticationToken authenticationToken =
          new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
      ;
      authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
      SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }
    filterChain.doFilter(request, response);
  }
}
