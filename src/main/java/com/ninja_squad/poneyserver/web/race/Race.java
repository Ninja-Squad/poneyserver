package com.ninja_squad.poneyserver.web.race;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * A race, which can be not started, started or finished, and which has 3 poneys.
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
        this.poneys = Collections.unmodifiableSet(new HashSet<>(poneys));
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public synchronized RaceStatus getStatus() {
        return status;
    }

    public synchronized void setStatus(RaceStatus status) {
        this.status = status;
    }

    public Set<String> getPoneys() {
        return poneys;
    }
}
