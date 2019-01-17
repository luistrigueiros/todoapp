package com.luist.todo.config;

import com.luist.todo.model.User;
import com.luist.todo.service.SimpleAuthenticationManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.authentication.session.CompositeSessionAuthenticationStrategy;
import org.springframework.security.web.authentication.session.RegisterSessionAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionFixationProtectionStrategy;

import java.util.LinkedList;
import java.util.List;

import static java.lang.String.format;

/**
 * This class is the main security configuration class. For more information of
 * how to configure the security, go to
 * https://docs.spring.io/spring-security/site/docs/current/reference/html/jc.html
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private SimpleAuthenticationManager authenticationManager;

    public SecurityConfig(SimpleAuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationManager);
    }


    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        String[] ALLOWED_BY_DEFAULT = {"/VAADIN/**", "/HEARTBEAT/**", "/UIDL/**", "/resources/**", "/login", "/login**", "/login/**"};
        http.csrf()
                .disable()
                .authorizeRequests()
                .antMatchers(ALLOWED_BY_DEFAULT).permitAll()
                .anyRequest().authenticated()
                .antMatchers("/main").access(userWithUseAppRoleExpression()).and()
                .formLogin()
                .loginPage("/login?auth")
                .permitAll()
                .defaultSuccessUrl("/main", true)
                .and()
                .sessionManagement()
                .sessionAuthenticationStrategy(sessionControlAuthenticationStrategy());
    }

    private static String userWithUseAppRoleExpression() {
        return format("hasAuthority('%s')", User.USE_APP_ROLE);
    }

    @Bean
    public SessionAuthenticationStrategy sessionControlAuthenticationStrategy() {
        SessionFixationProtectionStrategy sessionFixationProtectionStrategy = new SessionFixationProtectionStrategy();
        sessionFixationProtectionStrategy.setMigrateSessionAttributes(false);

        RegisterSessionAuthenticationStrategy registerSessionAuthenticationStrategy = new RegisterSessionAuthenticationStrategy(sessionRegistry());

        List<SessionAuthenticationStrategy> strategies = new LinkedList<>();
        strategies.add(sessionFixationProtectionStrategy);
        strategies.add(registerSessionAuthenticationStrategy);

        return new CompositeSessionAuthenticationStrategy(strategies);
    }

    @Bean
    public SessionRegistry sessionRegistry() {
        SessionRegistry sessionRegistry = new SessionRegistryImpl();
        return sessionRegistry;
    }

}