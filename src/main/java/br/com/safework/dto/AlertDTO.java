package br.com.safework.dto;

import br.com.safework.model.AlertSeverity;
import br.com.safework.model.AlertStatus;
import br.com.safework.model.AlertType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class AlertDTO {

    private Long id;

    @NotNull(message = "{alert.employeeId.notNull}")
    private Long employeeId;

    @NotNull(message = "{alert.type.notNull}")
    private AlertType type;

    @NotNull(message = "{alert.severity.notNull}")
    private AlertSeverity severity;

    // opcional no formulário – se vier nulo o serviço coloca OPEN
    private AlertStatus status;

    @Size(max = 500, message = "{alert.description.size}")
    private String description;

    // GETTERS E SETTERS

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public AlertType getType() {
        return type;
    }

    public void setType(AlertType type) {
        this.type = type;
    }

    public AlertSeverity getSeverity() {
        return severity;
    }

    public void setSeverity(AlertSeverity severity) {
        this.severity = severity;
    }

    public AlertStatus getStatus() {
        return status;
    }

    public void setStatus(AlertStatus status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
