package com.fooddelivery.userservice.dto.request;



import com.fooddelivery.userservice.model.User.Role;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class RegisterRequest {
	@NotBlank(message="Name is required")
	private String name;

	@NotBlank(message="Email is required")
	@Email(message="Invlaid email format")
	private String email;

	@NotBlank(message="Password is required")
	@Size(min=6, message="Password must be at least 6characters")
	private String password;

	@NotBlank(message="Phone is required")
	@Pattern(regexp="^[0-9]{10}$",message="Invalid phone number")
	private String phone;

	@NotNull(message="Role is required")
	private Role role;



	public RegisterRequest(@NotBlank(message = "Name is required") String name,
			@NotBlank(message = "Email is required") @Email(message = "Invlaid email format") String email,
			@NotBlank(message = "Password is required") @Size(min = 6, message = "Password must be at least 6characters") String password,
			@NotBlank(message = "Phone is required") @Pattern(regexp = "^[0-9]{10}$", message = "Invalid phone number") String phone,
			@NotNull(message = "Role is required") Role role) {
		super();
		this.name = name;
		this.email = email;
		this.password = password;
		this.phone = phone;
		this.role = role;
	}



	public RegisterRequest() {
		super();
	}





	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}




}
