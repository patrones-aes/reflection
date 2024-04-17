package co.edu.javeriana.example;

import java.util.List;

public class UserService {
    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public User createUser(User user) {
        return repository.create(User
                .builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .build());
    }

    public User readUserById(String id) {
        return repository.readById(id);
    }

    public List<User> readAllUsers() {
        return repository.readAll();
    }

    public void deleteUser(String id) {
        repository.delete(id);
    }
}
