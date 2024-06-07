package com.olisaude.testedevback.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;


import com.olisaude.testedevback.repository.UserRepository;

@Component
public class SecurityFilter extends OncePerRequestFilter {

  @Autowired
  private TokenService tokenService;

  @Autowired
  private UserRepository userRepository;

  @SuppressWarnings("null")
  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
    String token = this.recoverToken(request);

    if (token != null) {
      try {
        var login = tokenService.validateToken(token);
        logger.info("Trying to find the logged in user: " + login);
        UserDetails user = userRepository.findByLogin(login);

        if (user != null) {
            var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } else {
            logger.warn("User not found with login: " + login);
        }
      } catch (Exception e) {
          logger.error("Token validation failed: " + e.getMessage());
      }
    } else {
        logger.warn("Token not provided in the request.");
    }

    filterChain.doFilter(request, response);
  }

  private String recoverToken(HttpServletRequest request) {
      String authHeader = request.getHeader("Authorization");
      if (authHeader == null || !authHeader.startsWith("Bearer ")) {
        logger.warn("Authorization header is missing or does not start with Bearer");
        return null;
      }
      return authHeader.substring(7);
  }
}