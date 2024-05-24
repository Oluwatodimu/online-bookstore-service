package com.todimu.backend.bookstoreservice;

import com.todimu.backend.bookstoreservice.data.entity.Authority;
import com.todimu.backend.bookstoreservice.data.entity.User;
import com.todimu.backend.bookstoreservice.repository.AuthorityRepository;
import com.todimu.backend.bookstoreservice.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@SpringBootApplication
public class OnlineBookstoreServiceApplication {

	private static final String USER = "ROLE_USER";
	private static final String ADMIN = "ROLE_ADMIN";

	public static void main(String[] args) {
		SpringApplication.run(OnlineBookstoreServiceApplication.class, args);
	}


	@Bean
	public CommandLineRunner createUserRoles(AuthorityRepository authorityRepository) {
		return (args -> {
			List<Authority> authorities = authorityRepository.findAll();
			if (authorities.isEmpty()) {
				Authority userAuthority = new Authority();
				userAuthority.setId(1L);
				userAuthority.setAuthorityName(USER);

				Authority adminAuthority = new Authority();
				adminAuthority.setId(2L);
				adminAuthority.setAuthorityName(ADMIN);

				authorityRepository.save(userAuthority);
				authorityRepository.save(adminAuthority);
			}
		});
	}

	@Bean
	public CommandLineRunner createAdminUser(UserRepository userRepository, AuthorityRepository authorityRepository,
											 PasswordEncoder passwordEncoder) {
		return (args -> {
			Optional<User> adminUserOptional = userRepository.findByEmailIgnoreCase("admin@gmail.com");
			if (adminUserOptional.isEmpty()) {
				User user = User.builder()
						.email("admin@gmail.com")
						.password(passwordEncoder.encode("password123$"))
						.build();

				Set<Authority> authorities = new HashSet<>();
				authorityRepository.findByAuthorityName(ADMIN).ifPresent(authorities::add);
				user.setAuthorities(authorities);
				userRepository.save(user);
			}
		});
	}
}
