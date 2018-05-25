package com.excilys.formation.cdb.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.www.DigestAuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.DigestAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.NegatedRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;
import static com.excilys.formation.cdb.model.constants.SecurityParameters.ROLE_ADMIN;
import static com.excilys.formation.cdb.model.constants.SecurityParameters.ROLE_USER;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{

	private static final RequestMatcher PUBLIC_URLS = new OrRequestMatcher(
			new AntPathRequestMatcher("/login")
			);
	private static final RequestMatcher PROTECTED_URLS = new NegatedRequestMatcher(PUBLIC_URLS);


	@Autowired
	TokenAuthenticationProvider provider;
	
	@Autowired
	SimpleAuthenticationService simpleAuthenticationService;
	


	@Override
	protected void configure(final AuthenticationManagerBuilder auth) {
		auth.authenticationProvider(provider);
	}

	@Override
	public void configure(final WebSecurity web) {
		web.ignoring().requestMatchers(PUBLIC_URLS);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {   
		http.cors()
		.and()
		.sessionManagement()
		.sessionCreationPolicy(STATELESS)
		.and()
		.exceptionHandling()
		.defaultAuthenticationEntryPointFor(forbiddenEntryPoint(), PROTECTED_URLS)
		.and()
		.authenticationProvider(provider)
		.addFilterBefore(restAuthenticationFilter(), AnonymousAuthenticationFilter.class)
		.authorizeRequests()
		.antMatchers(HttpMethod.POST).hasAnyRole(ROLE_USER, ROLE_ADMIN)
		.antMatchers(HttpMethod.GET).hasAnyRole(ROLE_USER, ROLE_ADMIN)
		.antMatchers(HttpMethod.DELETE).hasRole(ROLE_ADMIN)
		.antMatchers(HttpMethod.PATCH).hasRole(ROLE_ADMIN)
		.antMatchers(HttpMethod.PUT).hasRole(ROLE_ADMIN)
		.and()
		.csrf().disable()
		.formLogin().disable()
		.httpBasic().disable()
		.logout().disable();
	}

	@Bean
	TokenAuthenticationFilter restAuthenticationFilter() throws Exception {
		final TokenAuthenticationFilter filter = new TokenAuthenticationFilter(PROTECTED_URLS);
		filter.setAuthenticationManager(authenticationManager());
		filter.setAuthenticationSuccessHandler(successHandler());
		return filter;
	}

	@Bean
	SimpleUrlAuthenticationSuccessHandler successHandler() {
		final SimpleUrlAuthenticationSuccessHandler successHandler = new SimpleUrlAuthenticationSuccessHandler();
		successHandler.setRedirectStrategy(new NoRedirectStrategy());
		return successHandler;
	}

	@Bean
	AuthenticationEntryPoint forbiddenEntryPoint() {
		return new HttpStatusEntryPoint(org.springframework.http.HttpStatus.UNAUTHORIZED);
	}
	

	@Bean
	public PasswordEncoder encoder() {
		return new BCryptPasswordEncoder(11);
	}



}
