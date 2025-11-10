package it.alf.cleana.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.alf.cleana.service.TodoService;

@RestController
@RequestMapping("/api/todos")
public class TodoController {
    private final TodoService service;

    public TodoController(TodoService service) {
        this.service = service;
    }

    @GetMapping
    public List<it.alf.cleana.dto.TodoDto> getAll() {
        return service.getAll().stream().map(it.alf.cleana.mapper.DtoMapper::toTodoDto).toList();
    }

    @PostMapping
    public ResponseEntity<it.alf.cleana.dto.TodoDto> create(@jakarta.validation.Valid @RequestBody it.alf.cleana.dto.CreateTodoDto item) {
        var entity = it.alf.cleana.mapper.DtoMapper.fromCreateTodoDto(item);
        var created = service.create(entity);
        var dto = it.alf.cleana.mapper.DtoMapper.toTodoDto(created);
        return ResponseEntity.created(URI.create("/api/todos/" + dto.getId())).body(dto);
    }

    @PutMapping("/{id}")
    public it.alf.cleana.dto.TodoDto update(@PathVariable Long id, @jakarta.validation.Valid @RequestBody it.alf.cleana.dto.CreateTodoDto item) {
        var entity = it.alf.cleana.mapper.DtoMapper.fromCreateTodoDto(item);
        var updated = service.update(id, entity);
        return it.alf.cleana.mapper.DtoMapper.toTodoDto(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
