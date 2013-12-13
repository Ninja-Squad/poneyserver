package com.ninja_squad.poneyserver.web.race;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.List;

/**
 * The race positions sent to all registered web socket clients while a race is running
 * @author JB Nizet
 */
public class RacePositions {
    private List<RacePosition> positions = new ArrayList<>();

    public RacePositions(Race race) {
        for (String poney : race.getPoneys()) {
            positions.add(new RacePosition(poney));
        }
    }

    public List<RacePosition> getPositions() {
        return positions;
    }

    @JsonIgnore
    public int getMaxPosition() {
        int max = -1;
        for (RacePosition position : positions) {
            if (position.getPosition() > max) {
                max = position.getPosition();
            }
        }
        return max;
    }
}
