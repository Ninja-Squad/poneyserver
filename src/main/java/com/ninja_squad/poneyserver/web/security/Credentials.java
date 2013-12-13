package com.ninja_squad.poneyserver.web.security;

/**
 * The credentials of a user, used during authentication
 * @author JB Nizet
 */
public class Credentials {
    private String login;
    private String password;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
