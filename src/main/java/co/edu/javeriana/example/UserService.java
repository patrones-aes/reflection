package co.edu.javeriana.example;

public class UserService {
    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public User saveUser(User user) {
        return repository.create(User
                .builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .build());
    }
}
