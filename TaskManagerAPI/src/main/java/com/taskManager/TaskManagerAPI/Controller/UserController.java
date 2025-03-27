package com.taskManager.TaskManagerAPI.Controller;

import com.taskManager.TaskManagerAPI.Pojo.User;
import com.taskManager.TaskManagerAPI.Service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    private UserService userService;

    public UserController(UserService userService)
    {
        this.userService = userService;
    }

    @GetMapping(path="/profile")
    public User getProfile(String username)
    {
        return userService.getUser(username);
    }
}
