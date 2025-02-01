package dev.runnerz.run;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryRunRepositoryTest {

    InMemoryRunRepository repository = new InMemoryRunRepository();

    @BeforeEach
    void setUp() {
        repository.create(new Run(1, "Morning Run", LocalDateTime.now(), LocalDateTime.now().plusMinutes(45), 5, Location.OUTDOOR, null));
        repository.create(new Run(2, "Evening Run", LocalDateTime.now(), LocalDateTime.now().plusMinutes(30), 2, Location.INDOOR, null));
    }


    @Test
    void shouldFindAllRuns() {
        assertEquals(2, repository.findAll().size(), "Should return 2 runs");
    }

    @Test
    void shouldCreateNewRun() {
        repository.create(new Run(3, "Afternoon Run", LocalDateTime.now(), LocalDateTime.now().plusMinutes(30), 3, Location.OUTDOOR, null));
        assertEquals(3, repository.findAll().size(), "Should return 3 runs");
    }

    @Test
    void shouldUpdateRun() {
        Run run = new Run(1, "Morning Run Updated", LocalDateTime.now(), LocalDateTime.now().plusMinutes(45), 12, Location.OUTDOOR, null);
        repository.update(run, 1);
        assertEquals("Morning Run Updated", repository.findById(1).get().title(), "Should return Morning Run Updated");
        assertEquals(12, repository.findById(1).get().miles(), "Should return 12 miles");
        assertEquals(Location.OUTDOOR, repository.findById(1).get().location(), "Should return OUTDOOR location");
    }

    @Test
    void shouldDeleteRun() {
        repository.delete(1);
        assertEquals(1, repository.findAll().size(), "Should return 1 run");
    }

}