package com.roms.authservice;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.roms.authservice.model.ERole;
import com.roms.authservice.model.Role;
import com.roms.authservice.model.User;
import com.roms.authservice.repository.RoleRepository;
import com.roms.authservice.repository.UserRepository;

@SpringBootApplication
public class AuthServiceApplication implements CommandLineRunner {

	@Autowired
	UserRepository userRepository;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	RoleRepository roleRepository;

	public static void main(String[] args) {
		SpringApplication.run(AuthServiceApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		User user = new User("User1", "user1@gmail.com", encoder.encode("User@123"));
		Set<Role> roles1 = new HashSet<>();
		Role userRole = roleRepository.findByName(ERole.USER)
				.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
		roles1.add(userRole);
		user.setRoles(roles1);
		userRepository.save(user);
		
		User moderator = new User("Moderator1", "moderator1@gmail.com", encoder.encode("Moderator@123"));
		Set<Role> roles2 = new HashSet<>();
		Role moderatorRole = roleRepository.findByName(ERole.MODERATOR)
				.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
		roles2.add(moderatorRole);
		moderator.setRoles(roles2);
		userRepository.save(moderator);
		
		User admin = new User("Admin1", "admin1@gmail.com", encoder.encode("Admin@123"));
		Set<Role> roles3 = new HashSet<>();
		Role adminRole = roleRepository.findByName(ERole.ADMIN)
				.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
		roles3.add(adminRole);
		admin.setRoles(roles3);
		userRepository.save(admin);
		
		User sakthi = new User("Sakthivel", "sakthivel@gmail.com", encoder.encode("Sakthivel@123"));
		Set<Role> roles4 = new HashSet<>();
		Role sakthiRole1 = roleRepository.findByName(ERole.USER)
				.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
		roles4.add(sakthiRole1);
		Role sakthiRole2 = roleRepository.findByName(ERole.MODERATOR)
				.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
		roles4.add(sakthiRole2);
		Role sakthiRole3 = roleRepository.findByName(ERole.ADMIN)
				.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
		roles4.add(sakthiRole3);
		sakthi.setRoles(roles4);
		userRepository.save(sakthi);
	}

}
