package com.softserve.if072.restservice.configuration;

import com.softserve.if072.restservice.security.CustomAuthenticationProcessingFilter;
import com.softserve.if072.restservice.security.CustomRESTAuthenticationManager;
import com.softserve.if072.restservice.security.handlers.CustomAccessDeniedHandler;
import com.softserve.if072.restservice.security.handlers.CustomAuthenticationFailureHandler;
import com.softserve.if072.restservice.security.handlers.CustomAuthenticationSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.xml.bind.annotation.adapters.HexBinaryAdapter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Configuration class for Spring Security framework
 *
 * @author Igor Parada
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@ComponentScan("com.softserve.if072.restservice.security")
@PropertySource("classpath:security.properties")
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("${security.securedURLPattern}")
    private String securedUrlPattern;

    @Value("${security.tokenHeaderName}")
    private String tokenHeaderName;

    @Value("${security.messageDigestAlgorithm}")
    private String messageDigestAlgorithm;

    @Autowired
    private CustomRESTAuthenticationManager customRESTAuthenticationManager;

    /**
     * Configures Spring HttpSecurity for using with RESTful service. Defines stateless session creation policy, sets
     * necessary authentication for secured URL pattern, disables any web-related stuff like forms and adds
     * custom authentication filter to the Spring Security FilterChain.
     *
     * @param http spring security http context
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(securedUrlPattern).authenticated()
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
    public AccessDeniedHandler getCustomAccessDeniedHandler() {
        return new CustomAccessDeniedHandler();
    }


    /**
     * Defines AbstractAuthenticationProcessingFilter bean with configured AuthenticationManager and success/failure handlers
     */
    @Bean
    public AbstractAuthenticationProcessingFilter customAuthenticationProcessingFilter() {
        CustomAuthenticationProcessingFilter filter =
                new CustomAuthenticationProcessingFilter(securedUrlPattern, tokenHeaderName);
        filter.setAuthenticationManager(customRESTAuthenticationManager);
        filter.setAuthenticationSuccessHandler(getAuthenticationSuccessHandler());
        filter.setAuthenticationFailureHandler(getAuthenticationFailureHandler());
        return filter;
    }

    /**
     * Creates MessageDigest bean based on pre-defined digest algorithm
     *
     * @return created MessageDigest instance
     */
    @Bean
    public MessageDigest messageDigest() {
        MessageDigest messageDigest;
        try {
            messageDigest = MessageDigest.getInstance(messageDigestAlgorithm);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Can't find message digest instance for " + messageDigestAlgorithm, e);
        }
        return messageDigest;
    }

    /**
     * Defines hexBinaryAdapter used for bi-directional converting byte array into UTF-8 string
     *
     * @return created adapter instance
     */
    @Bean
    public HexBinaryAdapter hexBinaryAdapter() {
        return new HexBinaryAdapter();
    }
}
