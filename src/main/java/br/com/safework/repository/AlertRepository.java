package br.com.safework.repository;

import br.com.safework.model.Alert;
import br.com.safework.model.AlertStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface AlertRepository extends JpaRepository<Alert, Long> {

    long countByStatus(AlertStatus status);

    long countByCreatedAtAfter(LocalDateTime since);

    Page<Alert> findAllByStatus(AlertStatus status, Pageable pageable);

    boolean existsByEmployeeId(Long employeeId);
}
