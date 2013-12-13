package com.ninja_squad.poneyserver.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 * REST controller for authentication
 * @author JB Nizet
 */
@RestController
@RequestMapping(value = "/authentication", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthenticationController {

    @Autowired
    private Database database;

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> authenticate(@RequestBody Credentials credentials, HttpServletResponse response) {
        for (User user : database.getUsers()) {
            if (user.getLogin().equals(credentials.getLogin())
                && user.getPassword().equals(credentials.getPassword())) {
                response.addCookie(new Cookie("AUTH-COOKIE", user.getLogin()));
                return new ResponseEntity<String>(user.getLogin(), HttpStatus.OK);
            }
        }
        return new ResponseEntity<String>(HttpStatus.UNAUTHORIZED);
    }
}
