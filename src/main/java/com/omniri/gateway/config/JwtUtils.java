package com.omniri.gateway.config;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.omniri.gateway.exception.TokenExpiredException;
import com.omniri.gateway.exception.TokenInvalidException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
@Component
public class JwtUtils implements Serializable{
	private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

	@Value("${hemkoder.app.jwtSecret}")
	private String jwtSecret;

	@Value("${hemkoder.app.jwtExpirationMs}")
	private int jwtExpirationMs;

	public String generateJwtToken( UserDetails userDetails) {

		//User userPrincipal = (User) userDetails.getUsername();
		Map<String, Object> claims = new HashMap<>();
	//	userDetails.getAuthorities().stream().forEach(authority-> claims.addauthority.getAuthority());r
		return Jwts.builder()
				.setClaims(claims)
				.setSubject((userDetails.getUsername()))
				.setIssuedAt(new Date())
				.setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
				.signWith(SignatureAlgorithm.HS512, jwtSecret)
				.compact();
	}

	public String getUserNameFromJwtToken(String token) {
		return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
	}

	
	
	public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = getAllClaimsFromToken(token);
		return claimsResolver.apply(claims);
	}

	// for retrieveing any information from token we will need the secret key
	private Claims getAllClaimsFromToken(String token) {
		return Jwts.parser().setSigningKey(jwtSecret)
		.parseClaimsJws(token).getBody();
	}

	// check if the token has expired
	private Boolean isTokenExpired(String token) {
		final Date expiration = getExpirationDateFromToken(token);
		return expiration.before(new Date());
	}
	// retrieve expiration date from jwt token
		public Date getExpirationDateFromToken(String token) {
			return getClaimFromToken(token, Claims::getExpiration);
		}

	public boolean validateJwtToken(String authToken) {
		try {
			Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
			return true;
		} catch (SignatureException e) {
			e.printStackTrace();
			logger.error("Invalid JWT signature: {}", e.getMessage());
		} catch (MalformedJwtException e) {
			e.printStackTrace();
			logger.error("Invalid JWT token: {}", e.getMessage());
		} catch (ExpiredJwtException e) {
			e.printStackTrace();
			logger.error("JWT token is expired: {}", e.getMessage());
		} catch (UnsupportedJwtException e) {
			e.printStackTrace();
			logger.error("JWT token is unsupported: {}", e.getMessage());
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			logger.error("JWT claims string is empty: {}", e.getMessage());
		}

		return false;
	}
	 public Boolean validateToken(final String username, UserDetails userDetails,String token) {
		    if(isTokenExpired(token)) {
		    	throw new TokenExpiredException();
		    }
		    if(!username.equals(userDetails.getUsername())) throw new TokenInvalidException();
		    System.out.println("validated ");
			return true;
		}
}