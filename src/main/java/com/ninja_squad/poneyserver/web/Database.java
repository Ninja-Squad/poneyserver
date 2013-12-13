package com.ninja_squad.poneyserver.web;

import com.ninja_squad.poneyserver.web.race.Bet;
import com.ninja_squad.poneyserver.web.race.Race;
import com.ninja_squad.poneyserver.web.race.RaceStatus;
import com.ninja_squad.poneyserver.web.user.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * The fake database, containing the races, bets and users.
 * @author JB Nizet
 */
@Service
public class Database {
    private List<User> users = new CopyOnWriteArrayList<>();
    private List<Race> races = new CopyOnWriteArrayList<>();

    private Map<BetKey, String> bets = new ConcurrentHashMap<>();

    private static final List<String> PONEYS = Arrays.asList("Red", "Green", "Yellow", "Pink", "Blue");

    private static class BetKey {
        private String login;
        private Long raceId;

        private BetKey(String login, Long raceId) {
            this.login = login;
            this.raceId = raceId;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }

            BetKey betKey = (BetKey) o;

            if (!login.equals(betKey.login)) {
                return false;
            }
            if (!raceId.equals(betKey.raceId)) {
                return false;
            }

            return true;
        }

        @Override
        public int hashCode() {
            int result = login.hashCode();
            result = 31 * result + raceId.hashCode();
            return result;
        }
    }

    public Database() {
        races.add(new Race(1L, "Course 1", RaceStatus.NOT_STARTED, randomPoneys()));
        races.add(new Race(2L, "Course 2", RaceStatus.NOT_STARTED, randomPoneys()));
        races.add(new Race(3L, "Course 3", RaceStatus.NOT_STARTED, randomPoneys()));
        races.add(new Race(4L, "Course 4", RaceStatus.NOT_STARTED, randomPoneys()));
        races.add(new Race(5L, "Course 5", RaceStatus.NOT_STARTED, randomPoneys()));
    }

    private static Set<String> randomPoneys() {
        List<String> list = new ArrayList<>(PONEYS);
        Collections.shuffle(list);
        Set<String> result = new HashSet<>();
        for (int i = 0; i < 3; i++) {
            result.add(list.get(i));
        }
        return result;
    }

    public synchronized void addUser(User user) {
        users.add(user);
    }

    public List<User> getUsers() {
        return new ArrayList<>(users);
    }

    public List<Race> getRaces() {
        return new ArrayList<>(races);
    }

    public Race getRace(Long raceId) {
        for (Race race : races) {
            if (race.getId().equals(raceId)) {
                return race;
            }
        }
        return null;
    }

    public void addBet(String login, Bet bet) {
        BetKey key = new BetKey(login, bet.getRaceId());
        bets.put(key, bet.getPoney());
    }

    public void deleteBet(String login, Long raceId) {
        BetKey key = new BetKey(login, raceId);
        bets.remove(key);
    }

    public String getBettedPoney(String login, Long raceId) {
        return bets.get(new BetKey(login, raceId));
    }
}
