package com.ninja_squad.poneyserver.web.race;

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
 * The controller allowing to start a race. Once a race is started, the positions of the poneys are broadcasted every
 * second to all the websocket clients having subscribed to /topic/[id of the race].
 * @author JB Nizet
 */
@Api("Start a race")
@RestController
@RequestMapping(value = "/api/running", produces = MediaType.APPLICATION_JSON_VALUE)
public class RunningRaceController {
    @Autowired
    private Database database;

    @Autowired
    private RaceRunner raceRunner;

    /**
     * Starts the given race. A 400 response with an error message is sent if the race has already been started.
     */
    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation("Starts the given race")
    @ApiResponses(@ApiResponse(code = 400, message = "The race is already started or finished"))
    private void startRace(@RequestBody Long raceId) {
        Race race = database.getRace(raceId);
        if (race.getStatus() == RaceStatus.READY) {
            raceRunner.startRace(race);
        }
        else {
            throw new BadRequestException("The race is already started or finished");
        }
    }
}
