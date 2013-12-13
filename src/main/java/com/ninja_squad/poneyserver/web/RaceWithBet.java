package com.ninja_squad.poneyserver.web;

/**
 * TODO include class javadoc here
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
