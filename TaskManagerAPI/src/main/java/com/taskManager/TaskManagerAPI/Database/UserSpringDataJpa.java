package com.taskManager.TaskManagerAPI.Database;

import com.taskManager.TaskManagerAPI.Pojo.Task;
import com.taskManager.TaskManagerAPI.Pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserSpringDataJpa extends JpaRepository<User,String> {
    public User findByUsername(String username);

    @Override
    <S extends User> S saveAndFlush(S entity);
}
