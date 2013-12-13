package com.ninja_squad.poneyserver.web.race;

/**
 * A race and the bet (which can be null) of the current user.
 * @author JB Nizet
 */
public class RaceWithBet {
    private Race race;
    private String bettedPoney;

    public RaceWithBet(Race race, String bettedPoney) {
        this.race = race;
        this.bettedPoney = bettedPoney;
    }

    public Race getRace() {
        return race;
    }

    public String getBettedPoney() {
        return bettedPoney;
    }
}
