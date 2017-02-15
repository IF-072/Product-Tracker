package com.softserve.if072.mvcapp.interceptor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@PropertySource(value = {"classpath:application.properties"})
public class AddTokenHeaderInterceptor implements ClientHttpRequestInterceptor {

    @Value("${application.authenticationCookieName}")
    private String headerName;


    @Override
    public ClientHttpResponse intercept(
            HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
            throws IOException {

        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpServletRequest clientRequest = attr.getRequest();

        Cookie[] cookies = clientRequest.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (headerName.equals(cookie.getName())) {
                    HttpHeaders headers = request.getHeaders();
                    headers.add(headerName,  cookie.getValue());
                }
            }
        }

        return execution.execute(request, body);
    }

}
