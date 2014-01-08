package com.ninja_squad.poneyserver.web.security;

import com.mangofactory.swagger.annotations.ApiError;
import com.mangofactory.swagger.annotations.ApiErrors;
import com.ninja_squad.poneyserver.web.Database;
import com.ninja_squad.poneyserver.web.user.User;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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

    /**
     * Authenticates the user with the given credentials, and sends back the token (the login of the user) which must
     * be sent by every subsequent request in a header named Custom-Authentication. If the credentials are invalid, a 401 response
     * is sent.
     */
    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Authenticates a user and gets back the 'secret' token in the response. This token must be passed in a header named 'Custom-Authentication' in all subsequent requests")
    @ApiErrors(errors = @ApiError(code = 401, reason = "The credentials are incorrect"))
    public String authenticate(@ApiParam(value = "The authentication credentials", required = true) @RequestBody Credentials credentials, HttpServletResponse response) {
        for (User user : database.getUsers()) {
            if (user.getLogin().equals(credentials.getLogin())
                && user.getPassword().equals(credentials.getPassword())) {
                return user.getLogin();
            }
        }
        throw new UnauthorizedException();
    }
}
