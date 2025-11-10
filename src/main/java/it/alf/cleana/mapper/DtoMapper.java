package it.alf.cleana.mapper;

import it.alf.cleana.domain.Priority;
import it.alf.cleana.domain.TodoItem;
import it.alf.cleana.dto.CreateTodoDto;
import it.alf.cleana.dto.TodoDto;

public final class DtoMapper {
    private DtoMapper() {}

    public static TodoDto toTodoDto(TodoItem item) {
        var dto = new TodoDto();
        dto.setId(item.getId());
        dto.setTitle(item.getTitle());
        dto.setDescription(item.getDescription());
        dto.setPriority(item.getPriority() != null ? item.getPriority().name() : null);
        dto.setCompleted(item.isCompleted());
        dto.setUserId(item.getUserId());
        dto.setDueDate(item.getDueDate());
        dto.setLabels(item.getLabels());
        dto.setCreatedAt(item.getCreatedAt());
        dto.setCompletedAt(item.getCompletedAt());
        return dto;
    }

    public static TodoItem fromCreateTodoDto(CreateTodoDto dto) {
        var item = new TodoItem();
        item.setTitle(dto.getTitle());
        item.setDescription(dto.getDescription());
        if (dto.getPriority() != null) {
            try {
                item.setPriority(Priority.valueOf(dto.getPriority().toUpperCase()));
            } catch (IllegalArgumentException ex) {
                // ignore, leave default
            }
        }
        item.setDueDate(dto.getDueDate());
        if (dto.getLabels() != null) item.setLabels(dto.getLabels());
        item.setCreatedAt(java.time.LocalDateTime.now());
        return item;
    }
}
