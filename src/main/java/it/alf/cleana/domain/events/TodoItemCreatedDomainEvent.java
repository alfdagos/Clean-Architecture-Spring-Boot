package it.alf.cleana.domain.events;

public class TodoItemCreatedDomainEvent {
    private final Long todoId;
    private final Long userId;

    public TodoItemCreatedDomainEvent(Long todoId, Long userId) {
        this.todoId = todoId;
        this.userId = userId;
    }

    public Long getTodoId() { return todoId; }
    public Long getUserId() { return userId; }
}
