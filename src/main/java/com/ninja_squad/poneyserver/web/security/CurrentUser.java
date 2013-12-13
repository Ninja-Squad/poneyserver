package com.ninja_squad.poneyserver.web.security;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

/**
 * The current user. This bean is a request-scope bean which is initialized by the authentication interceptor, thanks
 * to the cookie sent in the request.
 * @author JB Nizet
 */
@Component
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class CurrentUser {
    private String login;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }
}
