package hu.unideb.web;

import hu.unideb.model.Student;
import hu.unideb.repository.StudentRepository;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class StudentControllerImpl
        implements StudentController {

    private static final Logger LOGGER = LoggerFactory.getLogger(
            StudentControllerImpl.class
    );

    // @Autowired
    private final StudentRepository repository;

    public StudentControllerImpl(StudentRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Student> findAll() {
        LOGGER.info("findAll()");
        return repository.findAll();
    }

    @Override
    public Student findOne(String neptun) {
        LOGGER.info("findOne({})", neptun);
        return repository.findOne(neptun)
                .orElseThrow(() -> new IllegalArgumentException("Student not found"));
    }

    @Override
    public Student createOne(Student student) {
        LOGGER.info("createOne({})", student);
        return repository.createOne(student);
    }

    @Override
    public Student updateOne(Student updated) {
        LOGGER.info("updateOne({})", updated);
        return repository.updateOne(updated);
    }

    @Override
    public void deleteOne(String neptun) {
        LOGGER.info("deleteOne({})", neptun);
        repository.deleteOne(neptun);
    }

    @Override
    public List<Student> search(
            Optional<String> name,
            Optional<String> neptun,
            Optional<Student.Program> program) {
        LOGGER.info("search({}, {}, {})", name, neptun, program);

        return repository.findAll()
                .stream()
                .filter(student -> neptun
                        .map(s -> student.getNeptun().contains(s))
                        .orElse(true))
                .filter(student -> name
                        .map(s -> student.getName().contains(s))
                        .orElse(true))
                .filter(student -> program
                        .map(p -> p == student.getProgram())
                        .orElse(true))
                .toList();
    }

    @Override
    public ResponseEntity<String> download() {
        final var headers = new HttpHeaders();
        headers.set("Content-Type", "text/csv");
        headers.set("Content-Disposition",
                "attachment; filename=\"student.csv\"");

        return ResponseEntity
                .ok()
                .headers(headers)
                .body(repository.findAll()
                        .stream()
                        .map(student -> student.getNeptun() + ";"
                                + student.getName() + ";"
                                + student.getProgram())
                        .collect(Collectors.joining("\n")));
    }

    @Override
    public ResponseEntity<byte[]> getImage() {
        try {
            final var headers = new HttpHeaders();
            headers.set("Content-Disposition", "attachment; filename=\"apple.jpg\"");
            return ResponseEntity
                    .ok()
                    .headers(headers)
                    .body(IOUtils.toByteArray(Objects.requireNonNull(getClass().getResourceAsStream("apple.jpg"))));
        } catch (Exception ignored) {
            return ResponseEntity.notFound().build();
        }
    }
}
