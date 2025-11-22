package br.com.safework.service;

import br.com.safework.dto.AlertDTO;
import br.com.safework.model.Alert;
import br.com.safework.model.AlertStatus;
import br.com.safework.model.Employee;
import br.com.safework.repository.AlertRepository;
import br.com.safework.repository.EmployeeRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class AlertService {

    private final AlertRepository alertRepo;
    private final EmployeeRepository employeeRepo;

    // construtor explícito (sem Lombok)
    public AlertService(AlertRepository alertRepo, EmployeeRepository employeeRepo) {
        this.alertRepo = alertRepo;
        this.employeeRepo = employeeRepo;
    }

    public Page<Alert> list(Pageable pageable) {
        return alertRepo.findAll(pageable);
    }

    public long countOpen() {
        return alertRepo.countByStatus(AlertStatus.OPEN);
    }

    public long countToday() {
        return alertRepo.countByCreatedAtAfter(LocalDateTime.now().minusHours(24));
    }

    public Alert get(Long id) {
        return alertRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Alert " + id + " not found"));
    }

    @Transactional
    public Alert create(AlertDTO dto) {
        // Busca o funcionário
        Employee emp = employeeRepo.findById(dto.getEmployeeId())
                .orElseThrow(() -> new EntityNotFoundException("Employee " + dto.getEmployeeId() + " not found"));

        // Monta a entidade Alert "na mão"
        Alert alert = new Alert();
        alert.setType(dto.getType());
        alert.setSeverity(dto.getSeverity());
        alert.setStatus(dto.getStatus() == null ? AlertStatus.OPEN : dto.getStatus());
        alert.setEmployee(emp);
        alert.setDescription(dto.getDescription());
        alert.setCreatedAt(LocalDateTime.now());

        // Salva no banco
        return alertRepo.save(alert);
    }

    @Transactional
    public void resolve(Long id) {
        Alert alert = get(id);
        alert.setStatus(AlertStatus.RESOLVED);
        alertRepo.save(alert);
    }
}
