package com.taskManager.TaskManagerAPI.Database;

import com.taskManager.TaskManagerAPI.Pojo.Task;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskSpringDataJpa extends JpaRepository<Task,Long> {
    @Override
    public Task getReferenceById(Long id);

    @Override
    public Task getById(Long id);

    @Override
    <S extends Task> S saveAndFlush(S entity);

    @Override
    public void deleteById(Long id);

    public List<Task> findAllByAuthor(String author);

    public Task findByLabel(String label);

    @Override
    public Optional<Task> findById(Long id);
}
