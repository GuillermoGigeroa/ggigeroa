package ggigeroa.impresora.runner.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Security Configuration with hardcoded credentials
 * TODO: Replace with external authentication when ready for production
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

	/**
	 * Default hardcoded user credentials
	 */
	@Bean
	public UserDetailsService userDetailsService(PasswordEncoder encoder) {
		UserDetails user = User.builder()
				.username("ggigeroa")
				.password(encoder.encode("admin"))
				.roles("USER", "ADMIN")
				.build();

		return new InMemoryUserDetailsManager(user);
	}

	/**
	 * Password encoder
	 */
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	/**
	 * Security filter chain configuration
	 * - Swagger/OpenAPI endpoints: públicos
	 * - Health check (/): público
	 * - Resto: requiere autenticación Basic
	 */
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
				.csrf(csrf -> csrf.disable())
				.authorizeHttpRequests(authz -> authz
						.requestMatchers(
								"/",
								"/swagger-ui.html",
								"/swagger-ui/**",
								"/v3/api-docs/**",
								"/swagger-resources/**",
								"/webjars/**",
								"/h2-console/**")
						.permitAll()
						.anyRequest().authenticated())
				.headers(headers -> headers.frameOptions(frameOptions -> frameOptions.sameOrigin()))
				.httpBasic(basic -> {
				})
				.sessionManagement(session -> session
						.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

		return http.build();
	}
}
