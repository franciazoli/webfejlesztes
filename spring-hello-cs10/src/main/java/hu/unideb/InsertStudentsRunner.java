package hu.unideb;

import com.github.javafaker.Faker;
import hu.unideb.model.Student;
import hu.unideb.repository.StudentRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
@AllArgsConstructor
public class InsertStudentsRunner
        implements CommandLineRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(
            InsertStudentsRunner.class
    );

    private static final Random RANDOM = new Random();
    private static final String DIGITS = "0123456789";
    private static final String LETTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";


    private final StudentRepository repository;

    @Override
    public void run(String... args) throws Exception {
        final var faker = new Faker();

        final var now = OffsetDateTime.now();

        for (int i = 0; i < 100; i++) {
            final var name = faker.name();
            final var student = new Student(
                    IntStream.range(0, 6)
                            .mapToObj(
                                    pos -> pos == 0
                                    ? LETTERS.charAt(RANDOM.nextInt(26))
                                    : (LETTERS + DIGITS).charAt(RANDOM.nextInt(36))
                            )
                            .map(String::valueOf)
                            .collect(Collectors.joining()),

                    name.firstName() + " " + name.lastName(),
                    Student.Program.values()[RANDOM.nextInt(3)],
                    now, // placeholder
                    now // placeholder
            );

            LOGGER.info("Student was created {}", student);
            repository.createOne(student);

        }
    }
}
