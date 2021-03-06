package com.ninja_squad.poneyserver.web;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import com.ninja_squad.poneyserver.web.race.Bet;
import com.ninja_squad.poneyserver.web.race.Race;
import com.ninja_squad.poneyserver.web.race.RaceStatus;
import com.ninja_squad.poneyserver.web.user.User;
import org.springframework.stereotype.Service;

/**
 * The fake database, containing the races, bets and users.
 * @author JB Nizet
 */
@Service
public class Database {
    private List<User> users = new CopyOnWriteArrayList<>();
    private List<Race> races = new CopyOnWriteArrayList<>();

    private Map<BetKey, String> bets = new ConcurrentHashMap<>();

    private static final List<String> PONEYS = Arrays.asList("Orange", "Purple", "Yellow", "Pink", "Blue", "White");

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
        for (int i = 1; i <= 25; i++) {
            RaceStatus status =  i > 20 ? RaceStatus.FINISHED : RaceStatus.READY;
            races.add(new Race((long) i, "Course " + i, status, randomPoneys()));
        }
        User cedric = new User();
        cedric.setFirstName("Cédric");
        cedric.setLastName("Exbrayat");
        cedric.setLogin("ced");
        cedric.setPassword("password");
        cedric.setEmail("cedric@ninja-squad.com");
        cedric.setBirthYear(1986);
        users.add(cedric);
    }

    private static Set<String> randomPoneys() {
        List<String> list = new ArrayList<>(PONEYS);
        Collections.shuffle(list);
        Set<String> result = new HashSet<>();
        for (int i = 0; i < 5; i++) {
            result.add(list.get(i));
        }
        return result;
    }

    public synchronized void addUser(User user) {
        users.add(user);
    }

    public synchronized  List<User> getUsers() {
        return new ArrayList<>(users);
    }

    public synchronized List<Race> getRaces() {
        return new ArrayList<>(races);
    }

    public synchronized Race getRace(Long raceId) {
        for (Race race : races) {
            if (race.getId().equals(raceId)) {
                return race;
            }
        }
        return null;
    }

    public synchronized void addBet(String login, Bet bet) {
        BetKey key = new BetKey(login, bet.getRaceId());
        bets.put(key, bet.getPoney());
    }

    public synchronized void deleteBet(String login, Long raceId) {
        BetKey key = new BetKey(login, raceId);
        bets.remove(key);
    }

    public synchronized String getBettedPoney(String login, Long raceId) {
        return bets.get(new BetKey(login, raceId));
    }
}
