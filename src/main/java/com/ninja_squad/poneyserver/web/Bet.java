package com.ninja_squad.poneyserver.web;

/**
 * TODO include class javadoc here
 * @author JB Nizet
 */
public class Bet {
    private Long raceId;
    private String poney;

    public Long getRaceId() {
        return raceId;
    }

    public void setRaceId(Long raceId) {
        this.raceId = raceId;
    }

    public String getPoney() {
        return poney;
    }

    public void setPoney(String poney) {
        this.poney = poney;
    }
}
