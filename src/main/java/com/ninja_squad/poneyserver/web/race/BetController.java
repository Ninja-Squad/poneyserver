package com.ninja_squad.poneyserver.web.race;

import com.ninja_squad.poneyserver.web.Database;
import com.ninja_squad.poneyserver.web.security.CurrentUser;
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
 * Controller allowing the user to place or remove bets on poneys in races. Only one poney of a race can have a bet
 * from a given user. Placing a bet on another poney removes the previous bet, if any.
 * @author JB Nizet
 */
@RestController
@RequestMapping(value = "/bets", produces = MediaType.APPLICATION_JSON_VALUE)
public class BetController {

    @Autowired
    private Database database;

    @Autowired
    private CurrentUser currentUser;

    /**
     * Places a bet. If the race has already started, a 400 response with an error message is sent.
     */
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
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /**
     * Deletes a bet on a race.
     */
    @RequestMapping(value = "/{raceId}", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteBet(@PathVariable("raceId") Long raceId) {
        Race race = database.getRace(raceId);
        if (race.getStatus() != RaceStatus.NOT_STARTED) {
            return new ResponseEntity<>("The race doesn't accept bets anymore", HttpStatus.BAD_REQUEST);
        }

        database.deleteBet(currentUser.getLogin(), raceId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
