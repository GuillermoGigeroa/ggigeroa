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
			.username("leandro")
			.password(encoder.encode("impresora123"))
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
			.csrf().disable()
			.authorizeHttpRequests(authz -> authz
				.requestMatchers(
					"/",
					"/swagger-ui.html",
					"/swagger-ui/**",
					"/v3/api-docs/**",
					"/swagger-resources/**",
					"/webjars/**"
				).permitAll()
				.anyRequest().authenticated()
			)
			.httpBasic()
			.and()
			.sessionManagement()
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS);

		return http.build();
	}
}
