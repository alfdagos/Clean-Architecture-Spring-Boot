package it.alf.cleana.permission;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionRepository extends JpaRepository<Permission, Long> {
    List<Permission> findByUserId(Long userId);
    boolean existsByUserIdAndPermission(Long userId, String permission);
}
