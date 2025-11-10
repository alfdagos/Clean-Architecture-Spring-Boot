package it.alf.cleana.service;

import java.util.List;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import it.alf.cleana.auth.UserContext;
import it.alf.cleana.domain.TodoItem;
import it.alf.cleana.repository.TodoRepository;

@Service
public class TodoService {
    private final TodoRepository repo;
    private final UserContext userContext;
    private final ApplicationEventPublisher publisher;

    public TodoService(TodoRepository repo, UserContext userContext, ApplicationEventPublisher publisher) {
        this.repo = repo;
        this.userContext = userContext;
        this.publisher = publisher;
    }

    public List<TodoItem> getAll() {
        return repo.findAll();
    }

    public TodoItem create(TodoItem item) {
        // set owner and created timestamp from context
        item.setUserId(userContext.getUserId());
        if (item.getCreatedAt() == null) item.setCreatedAt(java.time.LocalDateTime.now());
        TodoItem created = repo.save(item);
        // publish domain events (if any)
        created.getDomainEvents().forEach(publisher::publishEvent);
        created.clearDomainEvents();
        return created;
    }

    public TodoItem update(Long id, TodoItem update) {
        return repo.findById(id)
            .map(existing -> {
                // Authorization: ensure current user can modify this todo
                // Note: this is a coarse check; for method-level security, we could use @PreAuthorize and PermissionService
                // but we keep a programmatic check here for parity with PermissionService usage.
                // If user is not owner, throw.
                Long currentUser = userContext.getUserId();
                if (existing.getUserId() != null && !existing.getUserId().equals(currentUser)) {
                    throw new SecurityException("User is not allowed to modify this todo");
                }
                existing.setTitle(update.getTitle());
                existing.setDescription(update.getDescription());
                existing.setPriority(update.getPriority());
                existing.setCompleted(update.isCompleted());
                existing.setDueDate(update.getDueDate());
                existing.setLabels(update.getLabels());
                if (update.isCompleted() && !existing.isCompleted()) {
                    existing.setCompletedAt(java.time.LocalDateTime.now());
                }
                TodoItem saved = repo.save(existing);
                saved.getDomainEvents().forEach(publisher::publishEvent);
                saved.clearDomainEvents();
                return saved;
            })
            .orElseThrow(() -> new IllegalArgumentException("Not found"));
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }
}
