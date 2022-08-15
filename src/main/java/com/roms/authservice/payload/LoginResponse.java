package com.roms.authservice.payload;

import java.util.List;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class LoginResponse {

	@NonNull
	private String accessToken;

	private String tokenType = "Bearer";

	@NonNull
	private Long id;

	@NonNull
	private String username;

	@NonNull
	private String email;

	@NonNull
	private List<String> roles;
}
