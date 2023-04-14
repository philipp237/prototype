package ru.annenkov.prototype.config;

import lombok.val;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	private static final String USER_ROLE = "USER";
	private static final String ADMIN_ROLE = "ADMIN";

	@Bean
	public InMemoryUserDetailsManager userDetailsManager() {
		val user1 = User.withUsername("user1")
				.password(passwordEncoder().encode("user1Pass"))
				.roles(USER_ROLE)
				.build();
		val user2 = User.withUsername("user2")
				.password(passwordEncoder().encode("user2Pass"))
				.roles(USER_ROLE)
				.build();
		val user3 = User.withUsername("user3")
				.password(passwordEncoder().encode("user3Pass"))
				.roles(ADMIN_ROLE)
				.build();
		return new InMemoryUserDetailsManager(user1, user2, user3);
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		return http.csrf()
				.disable()
				.authorizeRequests()
				.antMatchers("/admin/**").hasRole(ADMIN_ROLE)
				.antMatchers("/login").permitAll()
				.antMatchers("/").hasRole(USER_ROLE)
				.anyRequest()
				.authenticated()
				.and()
				.formLogin()
				.loginProcessingUrl("/login")
				.defaultSuccessUrl("/")
				.and()
				.logout()
				.logoutUrl("/logout")
				.deleteCookies("JSESSIONID")
				.and()
				.build();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
