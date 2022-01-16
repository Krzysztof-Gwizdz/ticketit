package pl.krzysztofgwizdz.ticketit.dto;

import pl.krzysztofgwizdz.ticketit.entity.User;

import java.util.ArrayList;
import java.util.List;

public class UserListDto {
    private List<User> users;

    public UserListDto() {
        users = new ArrayList<>();
    }

    public UserListDto(List<User> users) {
        this.users = users;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public void addUser(User user) {
        users.add(user);
    }
}
