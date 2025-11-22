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
                .orElseThrow(() -> new EntityNotFoundException("Alert " + id));
    }

    /**
     * Cria ou atualiza um alerta a partir do DTO.
     * Se dto.id for nulo -> cria novo.
     * Se dto.id tiver valor -> atualiza o existente.
     */
    @Transactional
    public Alert create(AlertDTO dto) {
        Employee emp = employeeRepo.findById(dto.getEmployeeId())
                .orElseThrow(() -> new EntityNotFoundException("Employee " + dto.getEmployeeId()));

        Alert alert;

        if (dto.getId() != null) {
            // edição
            alert = alertRepo.findById(dto.getId())
                    .orElseThrow(() -> new EntityNotFoundException("Alert " + dto.getId()));
        } else {
            // novo
            alert = new Alert();
            alert.setCreatedAt(LocalDateTime.now());
        }

        alert.setEmployee(emp);
        alert.setType(dto.getType());
        alert.setSeverity(dto.getSeverity());
        alert.setDescription(dto.getDescription());

        // status: se vier no DTO, usa; se não vier e ainda for null, assume OPEN
        if (dto.getStatus() != null) {
            alert.setStatus(dto.getStatus());
        } else if (alert.getStatus() == null) {
            alert.setStatus(AlertStatus.OPEN);
        }

        return alertRepo.save(alert);
    }

    @Transactional
    public void resolve(Long id) {
        Alert alert = alertRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Alert " + id));
        alert.setStatus(AlertStatus.RESOLVED);
        alertRepo.save(alert);
    }

    @Transactional
    public void delete(Long id) {
        alertRepo.deleteById(id);
    }
}
