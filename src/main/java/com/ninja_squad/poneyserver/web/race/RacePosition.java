package com.ninja_squad.poneyserver.web.race;

/**
 * The position of a poney while a race is running
 * @author JB Nizet
 */
public class RacePosition {
    private String poney;
    private int position;

    public RacePosition(String poney) {
        this.poney = poney;
    }

    public void move(int offset) {
        position += offset;
        if (position > 100) {
            position = 100;
        }
    }

    public String getPoney() {
        return poney;
    }

    public int getPosition() {
        return position;
    }
}
