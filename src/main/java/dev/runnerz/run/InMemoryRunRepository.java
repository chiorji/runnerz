package dev.runnerz.run;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class InMemoryRunRepository {
    private static final Logger log = LoggerFactory.getLogger(InMemoryRunRepository.class);
    private final List<Run> runs = new ArrayList<>();

    public List<Run> findAll() {
        return runs;
    }

    public Optional<Run> findById(Integer id) {
        return Optional.ofNullable(runs.stream()
                .filter(run -> run.id().equals(id))
                .findFirst()
                .orElseThrow(RunNotFoundException::new));
    }

    public void create(Run run) {
        Run newRun = new Run(run.id(), run.title(), run.startedOn(), run.completedOn(), run.miles(), run.location(), null);
        runs.add(newRun);
    }

    public void update(Run run, Integer id) {
        Optional<Run> existingRun = findById(id);
        if (existingRun.isPresent()) {
            var r = existingRun.get();
            log.info("Updating run: {}", existingRun.get());
            runs.set(runs.indexOf(r), run);
        }
    }

    public void delete(Integer id) {
        log.info("Deleting run: {}", findById(id).get());
        runs.removeIf(run -> run.id().equals(id));
    }

    public int count() {
        return runs.size();
    }

    public void saveAll(List<Run> runs) {
        runs.forEach(this::create);
    }

    public List<Run> findByLocation(String location) {
        return runs.stream()
                .filter(run -> run.location().equals(location))
                .toList();
    }

    @PostConstruct
    private void init() {
        create(new Run(1, "Morning Run", LocalDateTime.now(), LocalDateTime.now().plusMinutes(45), 5, Location.OUTDOOR, null));
        create(new Run(2, "Evening Run", LocalDateTime.now(), LocalDateTime.now().plusMinutes(20),  2, Location.INDOOR, null));
    }
}
