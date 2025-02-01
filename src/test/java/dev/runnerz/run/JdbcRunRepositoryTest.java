package dev.runnerz.run;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@JdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(JdbcRunRepository.class)
class JdbcRunRepositoryTest {

    @Autowired
    JdbcRunRepository jdbcRunRepository;

    @BeforeEach
    void setUp() {
        jdbcRunRepository.create(new Run(1, "Morning Run", LocalDateTime.now(), LocalDateTime.now().plusMinutes(30), 5, Location.OUTDOOR, null));
        jdbcRunRepository.create(new Run(2, "Evening Run", LocalDateTime.now(), LocalDateTime.now().plusMinutes(45), 3, Location.INDOOR, null));
    }

    @Test
    void shouldFindAllRuns() {
        List<Run> runs = jdbcRunRepository.findAll();
        assertEquals(2, runs.size());
    }

    @Test
    void shouldFindWithValidId() {
        var run = jdbcRunRepository.findById(1).get();
        assertEquals("Morning Run", run.title());
        assertEquals(5, run.miles());
    }

    @Test
    void shouldNotFindWithInValidId() {
        var run = jdbcRunRepository.findById(3);
        assertTrue(run.isEmpty());
    }

    @Test
    void shouldCreateNewRun() {
        jdbcRunRepository.create(new Run(3, "Afternoon Run", LocalDateTime.now(), LocalDateTime.now().plusMinutes(30), 3, Location.OUTDOOR, null));
        assertEquals(3, jdbcRunRepository.findAll().size());
    }

    @Test
    void shouldUpdateRun() {
        Run run = new Run(1, "Morning Run Updated", LocalDateTime.now(), LocalDateTime.now().plusMinutes(45), 12, Location.OUTDOOR, null);
        jdbcRunRepository.update(run, 1);
        assertEquals("Morning Run Updated", jdbcRunRepository.findById(1).get().title());
        assertEquals(12, jdbcRunRepository.findById(1).get().miles());
        assertEquals(Location.OUTDOOR, jdbcRunRepository.findById(1).get().location());
    }

    @Test
    void shouldDeleteRun() {
        jdbcRunRepository.delete(1);
        assertEquals(1, jdbcRunRepository.findAll().size());
    }
}