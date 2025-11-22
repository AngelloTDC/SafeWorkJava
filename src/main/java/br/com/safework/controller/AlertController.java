package br.com.safework.controller;

import br.com.safework.dto.AlertDTO;
import br.com.safework.model.Alert;
import br.com.safework.model.AlertSeverity;
import br.com.safework.model.AlertType;
import br.com.safework.repository.EmployeeRepository;
import br.com.safework.service.AlertService;
import jakarta.validation.Valid;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/alerts")
public class AlertController {

    private final AlertService service;
    private final EmployeeRepository employeeRepo;

    public AlertController(AlertService service, EmployeeRepository employeeRepo) {
        this.service = service;
        this.employeeRepo = employeeRepo;
    }

    @GetMapping
    public String list(@RequestParam(defaultValue = "0") int page,
                       @RequestParam(defaultValue = "10") int size,
                       Model model) {
        model.addAttribute("page",
                service.list(PageRequest.of(page, size, Sort.by("createdAt").descending())));
        return "alerts/list";
    }

    @GetMapping("/new")
    public String formNew(Model model) {
        model.addAttribute("alert", new AlertDTO());
        model.addAttribute("employees", employeeRepo.findAll(Sort.by("name")));
        model.addAttribute("types", AlertType.values());
        model.addAttribute("severities", AlertSeverity.values());
        return "alerts/form";
    }

    @GetMapping("/{id}/edit")
    public String formEdit(@PathVariable Long id, Model model) {
        Alert alert = service.get(id);
        AlertDTO dto = new AlertDTO();
        dto.setId(alert.getId());
        dto.setEmployeeId(alert.getEmployee().getId());
        dto.setType(alert.getType());
        dto.setSeverity(alert.getSeverity());
        dto.setStatus(alert.getStatus());
        dto.setDescription(alert.getDescription());

        model.addAttribute("alert", dto);
        model.addAttribute("employees", employeeRepo.findAll(Sort.by("name")));
        model.addAttribute("types", AlertType.values());
        model.addAttribute("severities", AlertSeverity.values());
        return "alerts/form";
    }

    @PostMapping
    public String save(@Valid @ModelAttribute("alert") AlertDTO dto,
                       BindingResult result,
                       Model model) {
        if (result.hasErrors()) {
            model.addAttribute("employees", employeeRepo.findAll(Sort.by("name")));
            model.addAttribute("types", AlertType.values());
            model.addAttribute("severities", AlertSeverity.values());
            return "alerts/form";
        }
        service.create(dto); // cria ou atualiza
        return "redirect:/alerts";
    }

    @PostMapping("/{id}/resolve")
    public String resolve(@PathVariable Long id) {
        service.resolve(id);
        return "redirect:/alerts";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        service.delete(id);
        return "redirect:/alerts";
    }
}
