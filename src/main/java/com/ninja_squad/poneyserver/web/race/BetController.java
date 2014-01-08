package com.ninja_squad.poneyserver.web.race;

import com.mangofactory.swagger.annotations.ApiError;
import com.mangofactory.swagger.annotations.ApiErrors;
import com.ninja_squad.poneyserver.web.BadRequestException;
import com.ninja_squad.poneyserver.web.Database;
import com.ninja_squad.poneyserver.web.security.CurrentUser;
import com.wordnik.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
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
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation("Places a bet on a poney in a race")
    @ApiErrors(errors = @ApiError(code = 400, reason = "The race doesn't accept bets, or the poney is not part of the race"))
    public void placeBet(@RequestBody Bet bet) {
        Race race = database.getRace(bet.getRaceId());
        if (race.getStatus() != RaceStatus.READY) {
            throw new BadRequestException("The race doesn't accept bets anymore");
        }
        if (!race.getPoneys().contains(bet.getPoney())) {
            throw new BadRequestException("The poney is not part of the race");
        }

        database.addBet(currentUser.getLogin(), bet);
    }

    /**
     * Deletes a bet on a race.
     */
    @RequestMapping(value = "/{raceId}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation("Cancels the current bet the given race")
    @ApiErrors(errors = @ApiError(code = 400, reason = "The race doesn't accept bets"))
    public void deleteBet(@PathVariable("raceId") Long raceId) {
        Race race = database.getRace(raceId);
        if (race.getStatus() != RaceStatus.READY) {
           throw new BadRequestException("The race doesn't accept bets anymore");
        }

        database.deleteBet(currentUser.getLogin(), raceId);
    }
}
