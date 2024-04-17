package co.edu.javeriana;

import co.edu.javeriana.example.Student;
import co.edu.javeriana.example.StudentRepository;
import co.edu.javeriana.example.StudentRepositoryImpl;
import co.edu.javeriana.example.StudentService;

import static co.edu.javeriana.annotation.impl.AnnotationImpl.createTable;

public class Main {
    public static void main(String[] args) {
        Student student = new Student();
        createTable(student);

        StudentRepository repository = new StudentRepositoryImpl();
        StudentService service = new StudentService(repository);

        service.createUser(new Student(1, "David", "Ramírez"));
        service.createUser(new Student(2, "Estefanía", "Bermúdez"));
        service.createUser(new Student(3, "Nicolás", "Ramírez"));
        service.createUser(new Student(4, "Javier", "Ramírez"));
        service.createUser(new Student(5, "Rebeca", "Monroy"));
        service.createUser(new Student(6, "Damaris", "Arroyo"));

        System.out.println(service.readAllUsers());
    }
}
