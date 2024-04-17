package co.edu.javeriana.example;

import co.edu.javeriana.repository.impl.RepositoryImpl;

public class StudentRepositoryImpl extends RepositoryImpl<Student, String> implements StudentRepository {
    public StudentRepositoryImpl() {
        super(Student.class);
    }
}
