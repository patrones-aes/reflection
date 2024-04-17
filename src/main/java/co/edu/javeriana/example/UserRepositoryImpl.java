package co.edu.javeriana.example;

import co.edu.javeriana.repository.impl.RepositoryImpl;

public class UserRepositoryImpl extends RepositoryImpl<User, String> implements UserRepository {
    public UserRepositoryImpl() {
        super(User.class);
    }
}
