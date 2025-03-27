package com.taskManager.TaskManagerAPI.DTO;
import java.time.LocalDate;

public class TaskDTO {
    private String label;
    private String description;
    private LocalDate dueDate;

    public TaskDTO() {
    }

    public TaskDTO(String author, String label, String description, LocalDate dueDate) {
        this.label = label;
        this.description = description;
        this.dueDate = dueDate;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }
}
