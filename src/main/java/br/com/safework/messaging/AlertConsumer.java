package br.com.safework.messaging;

import br.com.safework.dto.AlertDTO;
import br.com.safework.model.AlertStatus;
import br.com.safework.service.AlertService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class AlertConsumer {

    private final AlertService alertService;

    public AlertConsumer(AlertService alertService) {
        this.alertService = alertService;
    }

    @RabbitListener(queues = RabbitMQConfig.QUEUE)
    public void consume(AlertMessage msg) {
        AlertDTO dto = new AlertDTO();
        dto.setEmployeeId(msg.getEmployeeId());
        dto.setType(msg.getType());
        dto.setSeverity(msg.getSeverity());
        dto.setStatus(AlertStatus.OPEN);
        dto.setDescription(msg.getDescription());

        alertService.create(dto);
    }
}
