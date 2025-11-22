package br.com.safework.dto;

import br.com.safework.model.AlertSeverity;
import br.com.safework.model.AlertStatus;
import br.com.safework.model.AlertType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class AlertDTO {

    @NotNull
    private Long employeeId;

    @NotNull
    private AlertType type;

    @NotNull
    private AlertSeverity severity;

    // opcional; se vier null, o service assume AlertStatus.OPEN
    private AlertStatus status;

    @Size(max = 500)
    private String description;

    public AlertDTO() {
    }

    public AlertDTO(Long employeeId,
                    AlertType type,
                    AlertSeverity severity,
                    AlertStatus status,
                    String description) {
        this.employeeId = employeeId;
        this.type = type;
        this.severity = severity;
        this.status = status;
        this.description = description;
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
