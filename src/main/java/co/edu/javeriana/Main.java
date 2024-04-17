package co.edu.javeriana;

import co.edu.javeriana.example.User;
import co.edu.javeriana.example.UserRepository;
import co.edu.javeriana.example.UserRepositoryImpl;
import co.edu.javeriana.example.UserService;

import java.util.ArrayList;
import java.util.List;

import static co.edu.javeriana.annotation.impl.AnnotationImpl.createTable;

public class Main {
    public static void main(String[] args) {
        User user = new User();
        createTable(user);

        UserRepository repository = new UserRepositoryImpl();
        UserService service = new UserService(repository);

        List<User> users = new ArrayList<>();
        users.add(service.saveUser(new User("1", "David")));
        users.add(service.saveUser(new User("2", "Estefanía")));
        users.add(service.saveUser(new User("3", "Nicolás")));
        System.out.println(users);
    }
}
