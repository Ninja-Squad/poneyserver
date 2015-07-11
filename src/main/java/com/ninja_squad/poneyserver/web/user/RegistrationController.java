package com.ninja_squad.poneyserver.web.user;

import java.util.List;

import com.ninja_squad.poneyserver.web.BadRequestException;
import com.ninja_squad.poneyserver.web.Database;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * The controller which handle registration of new users
 * @author JB Nizet
 */
@Api("List and register users")
@RestController
@RequestMapping(value = "/api/users", produces = MediaType.APPLICATION_JSON_VALUE)
public class RegistrationController {

    @Autowired
    private Database database;

    /**
     * Registers a new user. A 400 response is sent with an error message if the login is already used by another user.
     * @param user the user to register.
     */
    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Registers a new user, and returns its 'secret' authentication token")
    @ApiResponses(@ApiResponse(code = 400, message = "The login is already in use"))
    public Token register(@RequestBody User user) {
        for (User existing : database.getUsers()) {
            if (user.getLogin().equals(existing.getLogin())) {
                throw new BadRequestException("This login is already in use");
            }
        }
        database.addUser(user);
        return new Token(user.getLogin());
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
