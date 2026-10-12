package com.fooddelivery.userservice.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.fooddelivery.userservice.dto.request.LoginRequest;
import com.fooddelivery.userservice.dto.request.RegisterRequest;
import com.fooddelivery.userservice.dto.response.AuthResponse;
import com.fooddelivery.userservice.model.User;
import com.fooddelivery.userservice.repository.UserRepository;
import com.fooddelivery.userservice.security.JwtUtil;


@Service
public class AuthService {
	private final AuthenticationProvider authenticationProvider;
	private final  UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtUtil jwtUtil;
	private final AuthenticationManager authenticationManager;
	private final UserDetailsService userDetailsService;

	public  AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil, AuthenticationManager authenticationManager,UserDetailsService userDetailsService, AuthenticationProvider authenticationProvider) {
		this.authenticationProvider = null;
		this.userRepository=userRepository;
		this.passwordEncoder=passwordEncoder;
		this.jwtUtil=jwtUtil;
		this.authenticationManager=authenticationManager;
		this.userDetailsService=userDetailsService;
	}

	public AuthResponse  register(RegisterRequest request) {
		if(userRepository.existsByEmail(request.getEmail())) {
			throw new RuntimeException("Email already registered!");
		}
		
		if(userRepository.existsByPhone(request.getPhone())) {
			throw new RuntimeException("Phone number already registered!");
		}
		
		User user =User.builder()
				.name(request.getName())
				.email(request.getEmail())
				.password(passwordEncoder.encode(request.getPassword()))
				.phone(request.getPhone())
				.role(request.getRole())
				.enabled(true)
				.build();

	    userRepository.save(user);

	    // Token generate pannuvoam
	    UserDetails userDetails = userDetailsService
	            .loadUserByUsername(user.getEmail());

	    String accessToken = jwtUtil.generateToken(userDetails,user.getId());
	    String refreshToken = jwtUtil.generateRefreshToken(userDetails);

	    return AuthResponse.builder()
	            .accessToken(accessToken)
	            .refreshToken(refreshToken)
	            .tokenType("Bearer")
	            .userId(user.getId())
	            .name(user.getName())
	            .email(user.getEmail())
	            .role(user.getRole())
	            .build();

		
	}

	public AuthResponse login(LoginRequest request) {
		  authenticationManager.authenticate(
	              new UsernamePasswordAuthenticationToken(
	                      request.getEmail(),
	                      request.getPassword()
	              )
	      );
		  
		  // User fetch pannuvoam
	      User user = userRepository.findByEmail(request.getEmail())
	              .orElseThrow(() -> new RuntimeException("User not found!"));

	      UserDetails userDetails = userDetailsService
	              .loadUserByUsername(user.getEmail());

	      String accessToken = jwtUtil.generateToken(userDetails,user.getId() );
	      String refreshToken = jwtUtil.generateRefreshToken(userDetails);

	      return AuthResponse.builder()
	              .accessToken(accessToken)
	              .refreshToken(refreshToken)
	              .tokenType("Bearer")
	              .userId(user.getId())
	              .name(user.getName())
	              .email(user.getEmail())
	              .role(user.getRole())
	              .build();
		  
	}


	public AuthResponse refreshToken(String refreshToken) {
	    String email = jwtUtil.extractUsername(refreshToken);
	    UserDetails userDetails = userDetailsService.loadUserByUsername(email);

	    if (jwtUtil.validateToken(refreshToken, userDetails)) {
	        User user = userRepository.findByEmail(email)
	                .orElseThrow(() -> new RuntimeException("User not found!"));

	        String newAccessToken = jwtUtil.generateToken(userDetails, user.getId());

	        return AuthResponse.builder()
	                .accessToken(newAccessToken)
	                .refreshToken(refreshToken)
	                .tokenType("Bearer")
	                .userId(user.getId())
	                .name(user.getName())
	                .email(user.getEmail())
	                .role(user.getRole())
	                .build();
	    }
	    throw new RuntimeException("Invalid refresh token!");
	}



}
