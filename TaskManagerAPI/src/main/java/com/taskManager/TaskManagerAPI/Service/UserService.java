package com.taskManager.TaskManagerAPI.Service;

import com.taskManager.TaskManagerAPI.Database.UserSpringDataJpa;
import com.taskManager.TaskManagerAPI.Pojo.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserSpringDataJpa userSpringDataJpa;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserSpringDataJpa userSpringDataJpa, PasswordEncoder passwordEncoder)
    {
        this.userSpringDataJpa = userSpringDataJpa;
        this.passwordEncoder = passwordEncoder;
    }

    public User getUser(String username)
    {
        return userSpringDataJpa.findByUsername(username);
    }

    public User addUser(User user)
    {
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        return userSpringDataJpa.saveAndFlush(user);
    }
}
