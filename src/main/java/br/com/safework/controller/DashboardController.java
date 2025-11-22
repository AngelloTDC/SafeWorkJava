package br.com.safework.controller;

import br.com.safework.repository.EmployeeRepository;
import br.com.safework.service.AlertService;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    private final EmployeeRepository employeeRepository;
    private final AlertService alertService;

    public DashboardController(EmployeeRepository employeeRepository,
                               AlertService alertService) {
        this.employeeRepository = employeeRepository;
        this.alertService = alertService;
    }

    @GetMapping("/")
    public String dashboard(Model model) {
        model.addAttribute("employeesCount", employeeRepository.count());
        model.addAttribute("alertsOpen", alertService.countOpen());
        model.addAttribute("alertsToday", alertService.countToday());
        model.addAttribute("safeHours", Math.max(0, employeeRepository.count() * 8)); // estimate

        model.addAttribute("lastAlerts",
                alertService.list(PageRequest.of(0, 5)).getContent());

        return "dashboard";
    }
}
