package it.alf.cleana.dto;

public class TodoDto {
    private Long id;
    private String title;
    private String description;
    private String priority;
    private boolean completed;
    private Long userId;
    private java.time.LocalDateTime dueDate;
    private java.util.List<String> labels;
    private java.time.LocalDateTime createdAt;
    private java.time.LocalDateTime completedAt;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getPriority() { return priority; }
    public void setPriority(String priority) { this.priority = priority; }

    public boolean isCompleted() { return completed; }
    public void setCompleted(boolean completed) { this.completed = completed; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public java.time.LocalDateTime getDueDate() { return dueDate; }
    public void setDueDate(java.time.LocalDateTime dueDate) { this.dueDate = dueDate; }

    public java.util.List<String> getLabels() { return labels; }
    public void setLabels(java.util.List<String> labels) { this.labels = labels; }

    public java.time.LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(java.time.LocalDateTime createdAt) { this.createdAt = createdAt; }

    public java.time.LocalDateTime getCompletedAt() { return completedAt; }
    public void setCompletedAt(java.time.LocalDateTime completedAt) { this.completedAt = completedAt; }
}
