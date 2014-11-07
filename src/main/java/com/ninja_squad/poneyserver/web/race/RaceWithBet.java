package com.ninja_squad.poneyserver.web.race;

import java.util.Set;

/**
 * A race and the bet (which can be null) of the current user.
 * @author JB Nizet
 */
public class RaceWithBet {

    // Note: we could have used JsonUnwrapped instead of copying all the Race field, but Swagger
    // doesn't understands it, and thus gives incorrect information

    private Long id;
    private String name;
    private RaceStatus status;
    private Set<String> poneys;
    private String bettedPoney;

    public RaceWithBet(Race race, String bettedPoney) {
        this.id = race.getId();
        this.name = race.getName();
        this.status = race.getStatus();
        this.poneys = race.getPoneys();
        this.bettedPoney = bettedPoney;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public RaceStatus getStatus() {
        return status;
    }

    public Set<String> getPoneys() {
        return poneys;
    }

    public String getBettedPoney() {
        return bettedPoney;
    }
}
