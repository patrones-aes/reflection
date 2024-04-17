package co.edu.javeriana.example;

import java.util.List;

public class StudentService {
    private final StudentRepository repository;

    public StudentService(StudentRepository repository) {
        this.repository = repository;
    }

    public Student createUser(Student student) {
        return repository.create(student);
    }

    public Student readUserById(String id) {
        return repository.readById(id);
    }

    public List<Student> readAllUsers() {
        return repository.readAll();
    }

    public void deleteUser(String id) {
        repository.delete(id);
    }
}
