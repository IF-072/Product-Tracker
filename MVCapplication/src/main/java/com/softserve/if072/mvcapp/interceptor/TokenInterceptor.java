package com.softserve.if072.mvcapp.interceptor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * The TokenInterceptor class is an {@link ClientHttpRequestInterceptor} implementation which adds stored in session token value
 * as a header to each RestTemplate's request.
 *
 * @author Igor Parada
 */

public class TokenInterceptor implements ClientHttpRequestInterceptor {

    /**
     * Defines the session attribute name which will store received from RESTapp token
     */
    @Value("${application.sessionAuthName}")
    private String sessionAuthName;

    /**
     * Looks the current user's session for attribute which stores token string,
     * and if it's found adds that attribute's value as a request header value to each RestTemplate's request.
     * Updates the token value if RESTapp's response contains appropriate header.
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
        HttpSession session = attr.getRequest().getSession();

        if (session.getAttribute(sessionAuthName) != null) {
            HttpHeaders headers = request.getHeaders();
            headers.add(sessionAuthName, session.getAttribute(sessionAuthName).toString());
        }
        ClientHttpResponse response = execution.execute(request, body);

        //renew token after each request
        renewToken(response, session);

        return response;
    }

    /**
     * Checks RestTemplate's response for token renewal header, and if it's found updates stored in session value.
     *
     * @param restTemplateResponse ClientHttpResponse instance received as result of RestTemplate's work
     * @param session              Current user's session
     */
    private void renewToken(ClientHttpResponse restTemplateResponse, HttpSession session) {
        HttpHeaders headers = restTemplateResponse.getHeaders();
        if (headers.containsKey(sessionAuthName)) {
            session.setAttribute(sessionAuthName, headers.get(sessionAuthName).get(0));
        }
    }
}
