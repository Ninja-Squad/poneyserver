package com.ninja_squad.poneyserver.web.user;

import com.mangofactory.swagger.annotations.ApiError;
import com.mangofactory.swagger.annotations.ApiErrors;
import com.ninja_squad.poneyserver.web.BadRequestException;
import com.ninja_squad.poneyserver.web.Database;
import com.wordnik.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * The controller which handle registration of new users
 * @author JB Nizet
 */
@RestController
@RequestMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
public class RegistrationController {

    @Autowired
    private Database database;

    /**
     * Registers a new user. A 400 response is sent with an error message if the login is already used by another user.
     * @param user the user to register.
     */
    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Registers a new user")
    @ApiErrors(errors = @ApiError(code = 400, reason = "The login is already in use"))
    public String register(@RequestBody User user) {
        for (User existing : database.getUsers()) {
            if (user.getLogin().equals(existing.getLogin())) {
                throw new BadRequestException("This login is already in use");
            }
        }
        database.addUser(user);
        return user.getLogin();
    }

    /**
     * Lists all the registered users
     */
    @ApiOperation(value = "Lists all the registered users")
    @RequestMapping(method = RequestMethod.GET)
    public List<User> list() {
        return database.getUsers();
    }
}
