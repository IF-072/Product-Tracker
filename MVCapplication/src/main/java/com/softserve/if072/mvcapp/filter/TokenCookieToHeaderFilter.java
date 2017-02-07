package com.softserve.if072.mvcapp.filter;

import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

/**
 * Filter grabs cookie with name "X-Token" and sets its value as request header's named "X-Token" value.
 */
public class TokenCookieToHeaderFilter extends OncePerRequestFilter {

    private static final String TOKEN_HEADER_NAME = "X-Token";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : request.getCookies()) {
                if (TOKEN_HEADER_NAME.equals(cookie.getName())) {
                    filterChain.doFilter(new AddTokenHeaderWrapper(request, cookie.getName(), cookie.getValue()), response);
                    return;
                }
            }
        }
        filterChain.doFilter(request, response);
    }

    private class AddTokenHeaderWrapper extends HttpServletRequestWrapper {

        private String headerName;
        private String headerValue;

        public AddTokenHeaderWrapper(HttpServletRequest request, String headerName, String headerValue) {
            super(request);
            this.headerName = headerName;
            this.headerValue = headerValue;
        }

        @Override
        public String getHeader(String name) {
            if (name.equals(headerName)) {
                return headerValue;
            }

            return super.getHeader(name);
        }

        @Override
        public Enumeration getHeaderNames() {
            List<String> names = Collections.list(super.getHeaderNames());
            names.add(this.headerName);
            return Collections.enumeration(names);
        }
    }
}
