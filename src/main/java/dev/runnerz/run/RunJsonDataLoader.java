package dev.runnerz.run;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;

@Component
public class RunJsonDataLoader implements CommandLineRunner {
    private static final Logger log = LoggerFactory.getLogger(RunJsonDataLoader.class);

    private final RunRepository runRepository;
    private final ObjectMapper objectMapper;

    public RunJsonDataLoader(RunRepository runRepository, ObjectMapper objectMapper) {
        this.runRepository = runRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("Size of runs: {}", runRepository.count());
        try (InputStream inputStream = TypeReference.class.getResourceAsStream("/data/runs.json")) {
            Runs allRuns = objectMapper.readValue(inputStream, Runs.class);
            runRepository.saveAll(allRuns.runs());
            log.info("Loaded {} Runs", allRuns.runs().size());
        } catch (IOException e) {
            throw new RuntimeException("Failed to load Runs", e);
        }
    }
}
