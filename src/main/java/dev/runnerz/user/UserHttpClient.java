package dev.runnerz.user;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;

import java.util.List;

public interface UserHttpClient {
    @GetExchange("/users")
    List<User> getUsers();

    @GetExchange("/users/{id}")
    User getUser(@PathVariable Integer id);
}
