package com.ninja_squad.poneyserver.web.race;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PreDestroy;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Service used to start a race. Starting a race starts a thread which updates the positions of the poneys in a random
 * way, from 0 to 100, every second. At each second, the position can increase of 1, 2 or 3.
 * @author JB Nizet
 */
@Service
public class RaceRunner {

    /**
     * The messaging template allowing to broadcast the running races positions.
     */
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    private ExecutorService executor = Executors.newCachedThreadPool();

    public void startRace(Race race) {
        race.setStatus(RaceStatus.STARTED);
        executor.submit(new RaceCallable(race));
    }

    @PreDestroy
    public void destroy() {
        executor.shutdownNow();
    }

    private class RaceCallable implements Callable<Void> {
        private Random random = new Random();
        private Race race;
        private RacePositions positions;

        private RaceCallable(Race race) {
            this.race = race;
            this.positions = new RacePositions(race);
        }

        @Override
        public Void call() throws Exception {
            while (positions.getMaxPosition() < 100) {
                Thread.sleep(1000L);
                for (RacePosition racePosition : positions.getPositions()) {
                    racePosition.move(random.nextInt(3) + 1);
                }
                messagingTemplate.convertAndSend("/topic/" + race.getId(), positions);
            }
            race.setStatus(RaceStatus.FINISHED);
            return null;
        }
    }
}
