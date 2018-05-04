package com.excilys.formation.cdb.config.security;

import com.excilys.formation.cdb.login.Privilege;
import com.excilys.formation.cdb.login.UserRole;
import com.excilys.formation.cdb.model.constants.Paths;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.www.DigestAuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.DigestAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private UserDetailsService userDetailsService;

    @Autowired
    public WebSecurityConfig(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(authenticationProvider());
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/resources/**"); // allows login to access to the resources
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
//                .antMatchers("/resources/**", Paths.LOCAL_PATH_403)
//                .permitAll()
                .antMatchers(Paths.LOCAL_PATH_LOGIN + "*")
                .anonymous()
                .antMatchers("/computer/**")
                .hasRole(UserRole.ADMIN.name())
                .antMatchers("/dashboard/**")
                .hasAnyRole(UserRole.ADMIN.name(), UserRole.USER.name())
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                .loginPage(Paths.LOCAL_PATH_LOGIN)
                .permitAll()
                .defaultSuccessUrl(Paths.LOCAL_PATH_DASHBOARD)
//                .failureUrl(Paths.LOCAL_PATH_LOGIN + "?error=true")
                .and()
                .logout()
                .logoutUrl("/logout")
                .permitAll()
//                .clearAuthentication(true)
//                .logoutUrl(Paths.ABSOLUTE_PATH_LOGOUT)
//                .logoutSuccessUrl(Paths.ABSOLUTE_PATH_LOGOUT + "?logout")
                .and()
                .csrf()
                .and()
                .exceptionHandling()
                .accessDeniedPage("/errors")
        ;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
