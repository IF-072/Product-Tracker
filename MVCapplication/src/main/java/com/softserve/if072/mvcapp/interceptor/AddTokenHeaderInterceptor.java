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

/**
 * The AddTokenHeaderInterceptor class is an {@link ClientHttpRequestInterceptor} implementation which adds
 * the specified request's cookie value as a request header value.
 *
 * @author Igor Parada
 */

@PropertySource(value = {"classpath:application.properties"})
public class AddTokenHeaderInterceptor implements ClientHttpRequestInterceptor {

    /**
     * Defines the cookie name which will be also set as header's name
     */
    @Value("${application.authenticationCookieName}")
    private String headerName;

    /**
     * Looks the current HttpServletRequest for cookie named exactly as specified by {@link #headerName} variable,
     * and if it's found adds that cookie value as a request header value.
     *
     * @param request   an {@link HttpRequest} instance
     * @param body      request's body as a byte array
     * @param execution an {@link ClientHttpRequestExecution} instance which will execute the modified request
     * @return execution result as {@link ClientHttpResponse}
     * @throws IOException may be produced during request execution
     */
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
                    headers.add(headerName, cookie.getValue());
                }
            }
        }

        return execution.execute(request, body);
    }

}
