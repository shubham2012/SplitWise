package dao;

import domain.User;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class UserDao {

    private static Map<UUID, User> users;

    public UserDao() {
        this.users = new HashMap<>();
    }

    public Map<UUID, User> getAllUser() {
        return users;
    }

    public User getUser(UUID uuid) {
        return users.get(uuid);
    }

    public void addUsers(User user) {
        this.users.put(user.getId(), user);
    }

}
