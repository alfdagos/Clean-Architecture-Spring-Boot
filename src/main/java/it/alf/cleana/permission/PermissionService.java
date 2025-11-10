package it.alf.cleana.permission;

import org.springframework.stereotype.Service;

import it.alf.cleana.auth.UserContext;
import it.alf.cleana.repository.TodoRepository;

@Service
public class PermissionService {
    private final PermissionRepository permissionRepository;
    private final UserContext userContext;
    private final TodoRepository todoRepository;

    public PermissionService(PermissionRepository permissionRepository, UserContext userContext, TodoRepository todoRepository) {
        this.permissionRepository = permissionRepository;
        this.userContext = userContext;
        this.todoRepository = todoRepository;
    }

    public boolean hasPermission(Long userId, String permission) {
        return permissionRepository.existsByUserIdAndPermission(userId, permission);
    }

    // Check whether current user can modify the given todo (owner or has 'todos.manage' permission)
    public boolean canModifyTodo(Long todoId) {
        Long currentUser = userContext.getUserId();
        return todoRepository.findById(todoId)
            .map(t -> t.getUserId() != null && t.getUserId().equals(currentUser))
            .orElse(false) || hasPermission(currentUser, "todos.manage");
    }
}
