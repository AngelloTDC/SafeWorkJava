package br.com.safework.service;

import br.com.safework.dto.EmployeeDTO;
import br.com.safework.model.Employee;
import br.com.safework.repository.EmployeeRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EmployeeService {

    private final EmployeeRepository repo;

    // construtor explícito – não depende de Lombok
    public EmployeeService(EmployeeRepository repo) {
        this.repo = repo;
    }

    @Cacheable("employees")
    public Page<Employee> list(Pageable pageable) {
        return repo.findAll(pageable);
    }

    @Cacheable(value = "employeeById", key = "#id")
    public Employee get(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Employee " + id));
    }

    @Transactional
    @CacheEvict(value = {"employees", "employeeById"}, allEntries = true)
    public Employee save(EmployeeDTO dto) {
        Employee e;

        if (dto.getId() != null) {
            // edição
            e = get(dto.getId());
        } else {
            // novo
            e = new Employee();
        }

        e.setName(dto.getName());
        e.setEmail(dto.getEmail());
        e.setRole(dto.getRole());
        e.setDepartment(dto.getDepartment());
        e.setStatus(dto.getStatus());
        e.setPhotoUrl(dto.getPhotoUrl());

        return repo.save(e);
    }

    @Transactional
    @CacheEvict(value = {"employees", "employeeById"}, allEntries = true)
    public void delete(Long id) {
        repo.deleteById(id);
    }
}
