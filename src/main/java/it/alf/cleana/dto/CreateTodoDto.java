package it.alf.cleana.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CreateTodoDto {
    @NotBlank
    @Size(max = 200)
    private String title;

    @Size(max = 2000)
    private String description;

    private String priority;
    private java.time.LocalDateTime dueDate;
    private java.util.List<String> labels;

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getPriority() { return priority; }
    public void setPriority(String priority) { this.priority = priority; }

    public java.time.LocalDateTime getDueDate() { return dueDate; }
    public void setDueDate(java.time.LocalDateTime dueDate) { this.dueDate = dueDate; }

    public java.util.List<String> getLabels() { return labels; }
    public void setLabels(java.util.List<String> labels) { this.labels = labels; }
}
