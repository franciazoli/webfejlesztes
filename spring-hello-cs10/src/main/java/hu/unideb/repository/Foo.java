package hu.unideb.repository;

import hu.unideb.model.Student;
import lombok.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

//@Repository
public class Foo implements StudentRepository{
    @Override
    public List<Student> findAll() {
        return null;
    }

    @Override
    public Optional<Student> findOne(@NonNull String neptun) {
        return Optional.empty();
    }

    @Override
    public Student createOne(@NonNull Student student) {
        return null;
    }

    @Override
    public Student updateOne(@NonNull Student student) {
        return null;
    }

    @Override
    public void deleteOne(@NonNull String neptun) {

    }
}
