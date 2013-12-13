package com.ninja_squad.poneyserver.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * TODO include class javadoc here
 * @author JB Nizet
 */
@RestController
@RequestMapping(value = "/running", produces = MediaType.APPLICATION_JSON_VALUE)
public class RunningRaceController {
    @Autowired
    private Database database;

    @RequestMapping(method = RequestMethod.POST)
    private ResponseEntity<String> startRace(@RequestBody Long raceId) {
        Race race = database.getRace(raceId);
        if (race.getStatus() == RaceStatus.NOT_STARTED) {
            race.setStatus(RaceStatus.STARTED);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        else {
            return new ResponseEntity<>("The race is already started or finished", HttpStatus.BAD_REQUEST);
        }
    }
}
