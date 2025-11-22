package br.com.safework.config;

import br.com.safework.model.Alert;
import br.com.safework.model.AlertSeverity;
import br.com.safework.model.AlertStatus;
import br.com.safework.model.AlertType;
import br.com.safework.model.Employee;
import br.com.safework.model.EmployeeStatus;
import br.com.safework.repository.AlertRepository;
import br.com.safework.repository.EmployeeRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    private final EmployeeRepository employeeRepo;
    private final AlertRepository alertRepo;

    // Construtor explícito (sem Lombok)
    public DataInitializer(EmployeeRepository employeeRepo, AlertRepository alertRepo) {
        this.employeeRepo = employeeRepo;
        this.alertRepo = alertRepo;
    }

    @Override
    public void run(String... args) {
        if (employeeRepo.count() > 0) return;

        // ---- Funcionário 1 ----
        Employee e1 = new Employee();
        e1.setName("João Silva");
        e1.setEmail("joao@safework.com");
        e1.setRole("Operador");
        e1.setDepartment("Chão de fábrica");
        e1.setStatus(EmployeeStatus.ACTIVE);
        e1 = employeeRepo.save(e1);

        // ---- Funcionário 2 ----
        Employee e2 = new Employee();
        e2.setName("Maria Souza");
        e2.setEmail("maria@safework.com");
        e2.setRole("Técnica");
        e2.setDepartment("Manutenção");
        e2.setStatus(EmployeeStatus.ACTIVE);
        e2 = employeeRepo.save(e2);

        // ---- Alertas iniciais ----
        Alert a1 = new Alert();
        a1.setEmployee(e1);
        a1.setType(AlertType.NO_HELMET);
        a1.setSeverity(AlertSeverity.HIGH);
        a1.setStatus(AlertStatus.OPEN);
        a1.setDescription("Sem capacete em área de risco.");
        a1.setCreatedAt(LocalDateTime.now());

        Alert a2 = new Alert();
        a2.setEmployee(e2);
        a2.setType(AlertType.FATIGUE);
        a2.setSeverity(AlertSeverity.MEDIUM);
        a2.setStatus(AlertStatus.INVESTIGATING);
        a2.setDescription("Sinais de fadiga detectados por IA.");
        a2.setCreatedAt(LocalDateTime.now());

        alertRepo.saveAll(List.of(a1, a2));
    }
}
