package com.roms.authservice.security.jwt;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.roms.authservice.exception.TokenInvalidException;
import com.roms.authservice.security.service.UserDetailsImpl;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JwtUtils {

	@Value("${authorization-service.app.jwtSecret}")
	private String jwtSecret;

	@Value("${authorization-service.app.jwtExpirationMs}")
	private int jwtExpirationMs;

	public String generateJwtToken(Authentication authentication) {

		UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
		log.info("Generating jwt token for user: " + userPrincipal.getUsername());
		return Jwts.builder().setSubject((userPrincipal.getUsername())).setIssuedAt(new Date())
				.setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
				.signWith(SignatureAlgorithm.HS512, jwtSecret).compact();
	}

	public String getUserNameFromJwtToken(String token) {
		return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
	}

	public boolean validateJwtToken(String authToken) throws TokenInvalidException {
		try {
			Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
			log.info("Valid jwt token");
			return true;
		} catch (SignatureException e) {
			throw new TokenInvalidException("Invalid JWT signature: " + e.getMessage());
		} catch (MalformedJwtException e) {
			throw new TokenInvalidException("Invalid JWT token: " + e.getMessage());
		} catch (ExpiredJwtException e) {
			throw new TokenInvalidException("JWT token expired: " + e.getMessage());
		} catch (UnsupportedJwtException e) {
			throw new TokenInvalidException("Unsupported JWT token: " + e.getMessage());
		} catch (IllegalArgumentException e) {
			throw new TokenInvalidException("JWT string empty: " + e.getMessage());
		}
	}
}
