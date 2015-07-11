package com.ninja_squad.poneyserver.web.race;

import java.util.List;

import com.ninja_squad.poneyserver.web.Database;
import com.ninja_squad.poneyserver.web.security.CurrentUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller used to list the races and to show a race
 * @author JB Nizet
 */
@Api("List and show races")
@RestController
@RequestMapping(value = "/api/races", produces = MediaType.APPLICATION_JSON_VALUE)
public class RaceController {

    @Autowired
    private Database database;

    @Autowired
    private CurrentUser currentUser;

    /**
     * Lists all the races
     */
    @ApiOperation("Lists the races")
    @RequestMapping(method = RequestMethod.GET)
    private List<Race> list() {
        return database.getRaces();
    }

    /**
     * Returns the race, with the potential bet of the current user, identified by the given ID.
     */
    @ApiOperation("Shows the details of the given race")
    @RequestMapping(value = "/{raceId}", method = RequestMethod.GET)
    private RaceWithBet show(@PathVariable("raceId") Long raceId) {
        Race race = database.getRace(raceId);
        String bettedPoney = database.getBettedPoney(currentUser.getLogin(), raceId);
        return new RaceWithBet(race, bettedPoney);
    }
}
