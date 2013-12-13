package com.ninja_squad.poneyserver.web.security;

import com.ninja_squad.poneyserver.web.Database;
import com.ninja_squad.poneyserver.web.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Interceptor which checks the presence and validity of a user cookie (named AUTH-COOKIE),
 * and initializes the current user. The cookie must contain a valid user login. If the cookie is not there or is
 * invalid, a 401 response is sent.
 * @author JB Nizet
 */
public class AuthenticationInterceptor implements HandlerInterceptor {

    @Autowired
    private CurrentUser currentUser;

    @Autowired
    private Database database;

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        Cookie authenticationCookie = findAuthenticationCookie(request);
        if (authenticationCookie == null) {
            response.sendError(HttpStatus.UNAUTHORIZED.value());
            return false;
        }
        if (!cookieValid(authenticationCookie)) {
            response.sendError(HttpStatus.UNAUTHORIZED.value());
            return false;
        }

        return true;
    }

    private Cookie findAuthenticationCookie(HttpServletRequest request) {
        if (request.getCookies() == null) {
            return null;
        }
        for (Cookie cookie : request.getCookies()) {
            if (cookie.getName().equals("AUTH-COOKIE")) {
                return cookie;
            }
        }
        return null;
    }

    private boolean cookieValid(Cookie authenticationCookie) {
        String value = authenticationCookie.getValue();
        for (User user : database.getUsers()) {
            if (user.getLogin().equals(value)) {
                currentUser.setLogin(value);
                return true;
            }
        }
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request,
            HttpServletResponse response,
                           Object handler,
                           ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response,
                                Object handler,
                                Exception ex) throws Exception {
    }
}
