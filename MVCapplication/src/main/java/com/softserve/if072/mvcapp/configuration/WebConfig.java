package com.softserve.if072.mvcapp.configuration;


import com.softserve.if072.mvcapp.interceptor.AddTokenHeaderInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.fasterxml.jackson.core.JsonEncoding.UTF8;

/**
 * Configuration class for Spring MVC framework
 *
 * @author Igor Parada
 */
@Configuration
@EnableWebMvc
@ComponentScan("com.softserve.if072.mvcapp")
public class WebConfig extends WebMvcConfigurerAdapter {

    /**
     * Configures static resources location paths
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/css/**").addResourceLocations("WEB-INF/resources/css/");
        registry.addResourceHandler("/js/**").addResourceLocations("WEB-INF/resources/js/");
        registry.addResourceHandler("/fonts/**").addResourceLocations("WEB-INF/resources/fonts/");
        registry.addResourceHandler("/img/**").addResourceLocations("WEB-INF/resources/img/");
    }

    /**
     * Creates new {@link org.springframework.web.multipart.MultipartResolver} instance
     *
     * @return created instance
     */
    @Bean
    public CommonsMultipartResolver multipartResolver() {
        CommonsMultipartResolver resolver = new CommonsMultipartResolver();
        resolver.setMaxUploadSizePerFile(1048576);  //1MB
        return resolver;
    }

    /**
     * Instantiates new {@link AddTokenHeaderInterceptor} object
     *
     * @return created interceptor instance
     */
    @Bean
    public AddTokenHeaderInterceptor addTokenHeaderInterceptor(){
        return new AddTokenHeaderInterceptor();
    }

    /**
     * Creates RestTemplate Bean with injected {@link AddTokenHeaderInterceptor} instance
     *
     * @return created RestTemplate instance
     */
    @Bean
    public RestTemplate restTemplate() {
        RestTemplate template = new RestTemplate();
        template.setInterceptors(Collections.singletonList(addTokenHeaderInterceptor()));
        return template;
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        StringHttpMessageConverter stringConverter = new StringHttpMessageConverter();
        stringConverter.setSupportedMediaTypes(Arrays.asList(new MediaType("text", "plain", Charset.forName("UTF-8"))));
        converters.add(stringConverter);
    }
}
