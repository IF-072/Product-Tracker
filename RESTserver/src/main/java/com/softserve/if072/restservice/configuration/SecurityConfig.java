package com.softserve.if072.restservice.configuration;

import com.softserve.if072.restservice.security.CustomAuthenticationProcessingFilter;
import com.softserve.if072.restservice.security.CustomRESTAuthenticationManager;
import com.softserve.if072.restservice.security.handlers.CustomAccessDeniedHandler;
import com.softserve.if072.restservice.security.handlers.CustomAuthenticationFailureHandler;
import com.softserve.if072.restservice.security.handlers.CustomAuthenticationSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableWebSecurity
@ComponentScan("com.softserve.if072.restservice.security")
@PropertySource("classpath:security.properties")
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String SECURED_URL_PATTERN = "security.securedURLPattern";

    @Autowired
    private Environment environment;

    @Autowired
    private CustomRESTAuthenticationManager customRESTAuthenticationManager;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(environment.getProperty(SECURED_URL_PATTERN)).authenticated()
                .and()
                .httpBasic().disable()
                .formLogin().disable()
                .rememberMe().disable()
                .addFilterBefore(customAuthenticationProcessingFilter(), BasicAuthenticationFilter.class)
                .exceptionHandling()
                .accessDeniedHandler(getCustomAccessDeniedHandler());
    }

    @Bean
    public AuthenticationSuccessHandler getAuthenticationSuccessHandler() {
        return new CustomAuthenticationSuccessHandler();
    }

    @Bean
    public AuthenticationFailureHandler getAuthenticationFailureHandler() {
        return new CustomAuthenticationFailureHandler();
    }

    @Bean
    public AbstractAuthenticationProcessingFilter customAuthenticationProcessingFilter() {
        CustomAuthenticationProcessingFilter filter = new CustomAuthenticationProcessingFilter(environment.getProperty(SECURED_URL_PATTERN));
        filter.setAuthenticationManager(customRESTAuthenticationManager);
        filter.setAuthenticationSuccessHandler(getAuthenticationSuccessHandler());
        filter.setAuthenticationFailureHandler(getAuthenticationFailureHandler());
        return filter;
    }

    @Bean
    public AccessDeniedHandler getCustomAccessDeniedHandler() {
        return new CustomAccessDeniedHandler();
    }
}
