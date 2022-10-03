package auth;

import exceptions.DatabaseException;

import java.util.List;

public interface UserManager {
    public void addUser(User user) throws DatabaseException;

    public boolean isValid(User user);

    public boolean isPresent(String username);

    public List<User> getUsers();
}