package com.fooddelivery.userservice.dto.response;

import java.util.UUID;

import com.fooddelivery.userservice.model.User.Role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthResponse {
	private String accessToken;
	private String refreshToken;
	private String tokenType="Bearer";
	private UUID userId;
	private String name;
	private String email;
	private Role role;
}
