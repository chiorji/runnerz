package dev.runnerz.run;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public class RunRepository {
    private final List<Run> runs = new ArrayList<>();

    List<Run> findAll() {
        return runs;
    }

    Optional<Run> findById(Integer id) {
        return runs.stream().filter(run -> Objects.equals(run.id(), id)).findFirst();
    }

    void create(Run run) {
        runs.add(run);
    }

    void update(Run run, Integer id) {
        Optional<Run> existingRun = findById(id);
        if (existingRun.isEmpty()) {
            throw new IllegalArgumentException("Run not found");
        }
        runs.set(runs.indexOf(existingRun.get()), run);
    }

    void delete(Integer id) {
        runs.removeIf(run -> run.id().equals(id));
    }

    @PostConstruct
    private void init() {
        runs.add(new Run(1, "Monday Morning Run", LocalDateTime.now(), LocalDateTime.now().plusMinutes(30), 3, Location.OUTDOOR));
        runs.add(new Run(2, "Wednesday Morning Run", LocalDateTime.now(), LocalDateTime.now().plusHours(1), 5, Location.INDOOR));
        runs.add(new Run(3, "Saturday Morning Run", LocalDateTime.now(), LocalDateTime.now().plusHours(1), 5, Location.INDOOR));
    }
}
