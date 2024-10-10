package hu.unideb.web;

import hu.unideb.model.Student;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

public interface StudentController {
    @GetMapping("/student")
    List<Student> findAll();

    @GetMapping("/student/{neptun}")
    Student findOne(@PathVariable String neptun);

    @PutMapping("/student")
    Student createOne(@RequestBody Student student);

    @PostMapping("/student")
    Student updateOne(@RequestBody Student updated);

    @DeleteMapping("/student/{neptun}")
    void deleteOne(@PathVariable String neptun);

    @GetMapping("/search")
    List<Student> search(
            @RequestParam Optional<String> name,
            @RequestParam Optional<String> neptun,
            @RequestParam Optional<Student.Program> program);

    @GetMapping("/download")
    ResponseEntity<String> download();

    @GetMapping(path = "/image", produces = "image/jpeg")
    ResponseEntity<byte[]> getImage();
}
