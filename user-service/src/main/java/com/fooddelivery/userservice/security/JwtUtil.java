package com.fooddelivery.userservice.security;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

	@Value("${jwt.secret}")
	private String secret;

	@Value("${jwt.expiration}")
	private long expiration;

	@Value("${jwt.refresh-expiration}")
	private long refreshExpiration;

	// Generate access token — userId kூட pack pannறom
	public String generateToken(UserDetails userDetails, UUID userId) {
		Map<String, Object> claims = new HashMap<>();
		claims.put("role", userDetails.getAuthorities().iterator().next().getAuthority());
		claims.put("userId", userId.toString());
		return buildToken(claims, userDetails.getUsername(), expiration);
	}

	// Generate refresh token
	public String generateRefreshToken(UserDetails userDetails) {
		return buildToken(new HashMap<>(), userDetails.getUsername(), refreshExpiration);
	}

	private String buildToken(Map<String, Object> claims, String subject, long expiry) {
		return Jwts.builder()
				.claims(claims)
				.subject(subject)
				.issuedAt(new Date())
				.expiration(new Date(System.currentTimeMillis() + expiry))
				.signWith(getSignKey())
				.compact();
	}

	public boolean validateToken(String token, UserDetails userDetails) {
		final String username = extractUsername(token);
		return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
	}

	public String extractUsername(String token) {
		return extractClaim(token, Claims::getSubject);
	}

	public String extractUserId(String token) {
		return extractClaim(token, claims -> claims.get("userId", String.class));
	}

	public String extractRole(String token) {
		return extractClaim(token, claims -> claims.get("role", String.class));
	}

	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}

	private Claims extractAllClaims(String token) {
		return Jwts.parser()
				.verifyWith(getSignKey())
				.build()
				.parseSignedClaims(token)
				.getPayload();
	}

	private boolean isTokenExpired(String token) {
		return extractClaim(token, Claims::getExpiration).before(new Date());
	}

	public boolean isTokenValid(String token) {
		try {
			return !isTokenExpired(token);
		} catch (Exception e) {
			return false;
		}
	}

	private SecretKey getSignKey() {
		byte[] keyBytes = Decoders.BASE64.decode(secret);
		return Keys.hmacShaKeyFor(keyBytes);
	}
}