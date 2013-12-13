package com.ninja_squad.poneyserver.web.race;

import com.ninja_squad.poneyserver.web.Database;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * The controller allowing to start a race. Once a race is started, the positions of the poneys are broadcasted every
 * second to all the websocket clients having subscribed to /topic/[id of the race].
 * @author JB Nizet
 */
@RestController
@RequestMapping(value = "/running", produces = MediaType.APPLICATION_JSON_VALUE)
public class RunningRaceController {
    @Autowired
    private Database database;

    @Autowired
    private RaceRunner raceRunner;

    /**
     * Starts the given race. A 400 response with an error message is sent if the race has already been started.
     */
    @RequestMapping(method = RequestMethod.POST)
    private ResponseEntity<String> startRace(@RequestBody Long raceId) {
        Race race = database.getRace(raceId);
        if (race.getStatus() == RaceStatus.NOT_STARTED) {
            raceRunner.startRace(race);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        else {
            return new ResponseEntity<>("The race is already started or finished", HttpStatus.BAD_REQUEST);
        }
    }
}
