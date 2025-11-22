package br.com.safework.controller;

import br.com.safework.dto.EmployeeDTO;
import br.com.safework.model.Employee;
import br.com.safework.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeService service;

    // 👇 construtor explícito para injeção de dependência
    public EmployeeController(EmployeeService service) {
        this.service = service;
    }

    @GetMapping
    public String list(@RequestParam(defaultValue = "0") int page,
                       @RequestParam(defaultValue = "10") int size,
                       Model model) {
        model.addAttribute("page",
                service.list(PageRequest.of(page, size, Sort.by("name"))));
        return "employees/list";
    }

    @GetMapping("/new")
    public String formNew(Model model) {
        model.addAttribute("employee", new EmployeeDTO());
        return "employees/form";
    }

    @GetMapping("/{id}/edit")
    public String formEdit(@PathVariable Long id, Model model) {
        Employee e = service.get(id);
        EmployeeDTO dto = new EmployeeDTO();
        dto.setId(e.getId());
        dto.setName(e.getName());
        dto.setEmail(e.getEmail());
        dto.setRole(e.getRole());
        dto.setDepartment(e.getDepartment());
        dto.setStatus(e.getStatus());
        dto.setPhotoUrl(e.getPhotoUrl());
        model.addAttribute("employee", dto);
        return "employees/form";
    }

    @PostMapping
    public String save(@Valid @ModelAttribute("employee") EmployeeDTO dto,
                       BindingResult result) {
        if (result.hasErrors()) {
            return "employees/form";
        }
        service.save(dto);
        return "redirect:/employees";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        service.delete(id);
        return "redirect:/employees";
    }
}
