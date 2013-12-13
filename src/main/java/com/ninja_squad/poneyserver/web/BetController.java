package com.ninja_squad.poneyserver.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * TODO include class javadoc here
 * @author JB Nizet
 */
@RestController
@RequestMapping(value = "/bets", produces = MediaType.APPLICATION_JSON_VALUE)
public class BetController {

    @Autowired
    private Database database;

    @Autowired
    private CurrentUser currentUser;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<String> placeBet(@RequestBody Bet bet) {
        Race race = database.getRace(bet.getRaceId());
        if (race.getStatus() != RaceStatus.NOT_STARTED) {
            return new ResponseEntity<>("The race doesn't accept bets anymore", HttpStatus.BAD_REQUEST);
        }
        if (!race.getPoneys().contains(bet.getPoney())) {
            throw new IllegalArgumentException("The poney is not part of the race");
        }

        database.addBet(currentUser.getLogin(), bet);
        return new ResponseEntity<String>(HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{raceId}", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteBet(@PathVariable("raceId") Long raceId) {
        Race race = database.getRace(raceId);
        if (race.getStatus() != RaceStatus.NOT_STARTED) {
            return new ResponseEntity<>("The race doesn't accept bets anymore", HttpStatus.BAD_REQUEST);
        }

        database.deleteBet(currentUser.getLogin(), raceId);
        return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
    }
}
