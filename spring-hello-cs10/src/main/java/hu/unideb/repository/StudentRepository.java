package hu.unideb.repository;

import hu.unideb.model.Student;
import lombok.NonNull;

import java.util.List;
import java.util.Optional;

public interface StudentRepository {
    List<Student> findAll();

    Optional<Student> findOne(
            @NonNull String neptun);

    Student createOne(
            @NonNull Student student);

    Student updateOne(
            @NonNull Student student);

    void deleteOne(
            @NonNull String neptun);
}
