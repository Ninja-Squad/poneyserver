package com.ninja_squad.poneyserver.web;

import java.util.HashSet;
import java.util.Set;

/**
 * TODO include class javadoc here
 * @author JB Nizet
 */
public class Race {
    private Long id;
    private String name;
    private RaceStatus status;
    private Set<String> poneys;

    public Race(Long id, String name, RaceStatus status, Set<String> poneys) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.poneys = new HashSet<>(poneys);
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

    public void setStatus(RaceStatus status) {
        this.status = status;
    }

    public Set<String> getPoneys() {
        return poneys;
    }
}
