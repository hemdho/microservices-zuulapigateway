package com.omniri.gateway.config;

import java.io.IOException;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.omniri.gateway.bean.User;
import com.omniri.gateway.repository.UserRepository;
public class AuthTokenFilter extends OncePerRequestFilter {

	@Autowired
  private JwtUtils jwtUtils;

	
  @Autowired
  UserRepository userRepo;
  
  private static final Logger logger = LoggerFactory.getLogger(AuthTokenFilter.class);

 
  
  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    try {
    	System.out.println(" Readed heer");
      String jwt = parseJwt(request);
      System.out.println(jwt);
      if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
        String username = jwtUtils.getUserNameFromJwtToken(jwt);
        System.out.println("aaa " + username);
        
        List<User> users = userRepo.findByToken(jwt);
        User user = null;
        if(users!=null && users.size()>0) user= users.stream().filter(user_-> user_.getUsername().equals(username)).findFirst().orElse(null); 
        System.out.println(user.getUsername());
        if(user!=null && user.getUsername().equals(username)) {
        	UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(username, null,null);
        	authentication.setDetails(jwt);
        	SecurityContextHolder.getContext().setAuthentication(authentication);
        }else {
        	throw new BadCredentialsException("Bad credential");
        }
        
        	
        	
        		
        		
        
      }
    } catch (Exception e) {
    	e.printStackTrace();
    	throw e;
     // logger.error("Cannot set user authentication: {}", e);
    }

    filterChain.doFilter(request, response);
  }

  private String parseJwt(HttpServletRequest request) {
    String headerAuth = request.getHeader("Authorization");
    System.out.println(" Auth Header " + headerAuth);
    if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
      return headerAuth.substring(7, headerAuth.length());
    }

    return null;
  }
 
}
