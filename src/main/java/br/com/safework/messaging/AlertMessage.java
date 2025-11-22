package br.com.safework.messaging;

import br.com.safework.model.AlertSeverity;
import br.com.safework.model.AlertType;

public class AlertMessage {

    private Long employeeId;
    private AlertType type;
    private AlertSeverity severity;
    private String description;

    public AlertMessage() {
    }

    public AlertMessage(Long employeeId,
                        AlertType type,
                        AlertSeverity severity,
                        String description) {
        this.employeeId = employeeId;
        this.type = type;
        this.severity = severity;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
