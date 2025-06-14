package com.alibou.security;

import com.alibou.security.service.JPA.AuthenticationServiceJPA;
import com.alibou.security.model.request.RegisterRequest;
import com.alibou.security.entity.Role;
import com.alibou.security.repository.RoleRepository;
import com.alibou.security.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
@EnableScheduling
public class SecurityApplication {

	public static void main(String[] args) {
		SpringApplication.run(SecurityApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(
			AuthenticationServiceJPA service,
			RoleRepository roleRepository,
			UserRepository userRepository
	) {
		return args -> {
			if (roleRepository.count() == 0) {
				Role adminRole = new Role();
				adminRole.setName("ADMIN");
				roleRepository.save(adminRole);

				Role managerRole = new Role();
				managerRole.setName("MANAGER");
				roleRepository.save(managerRole);

				Role userRole = new Role();
				userRole.setName("USER");
				roleRepository.save(userRole);
			}

			if (userRepository.count() == 0) {
				var admin = RegisterRequest.builder()
						.username("Admin")
//						.fullName("Admin")
						.email("admin@mail.com")
						.phone("1234567890")
						.day("01")
						.month("01")
						.year("2000")
						.password("password")
						.role("ADMIN")
						.build();
				System.out.println("Admin token: " + service.register(admin).getAccessToken());

				var manager = RegisterRequest.builder()
						.username("Manager")
//						.fullName("Manager")
						.email("manager@gmail.com")
						.phone("0123456789")
						.day("01")
						.month("01")
						.year("2000")
						.password("password")
						.role("MANAGER")
						.build();
				System.out.println("Manager token: " + service.register(manager).getAccessToken());
			}
		};
	}
}