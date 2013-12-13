package com.ninja_squad.poneyserver.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * TODO include class javadoc here
 * @author JB Nizet
 */
@RestController
@RequestMapping(value = "/races", produces = MediaType.APPLICATION_JSON_VALUE)
public class RaceController {

    @Autowired
    private Database database;

    @Autowired
    private CurrentUser currentUser;

    @RequestMapping(method = RequestMethod.GET)
    private List<Race> list() {
        return database.getRaces();
    }

    @RequestMapping(value = "/{raceId}", method = RequestMethod.GET)
    private RaceWithBet show(@PathVariable("raceId") Long raceId) {
        Race race = database.getRace(raceId);
        String bettedPoney = database.getBettedPoney(currentUser.getLogin(), raceId);
        return new RaceWithBet(race, bettedPoney);
    }
}
