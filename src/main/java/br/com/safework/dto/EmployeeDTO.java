package br.com.safework.dto;

import br.com.safework.model.EmployeeStatus;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class EmployeeDTO {

    private Long id;

    @NotBlank(message = "{employee.name.notBlank}")
    @Size(max = 120, message = "{employee.name.size}")
    private String name;

    @NotBlank(message = "{employee.email.notBlank}")
    @Email(message = "{employee.email.valid}")
    @Size(max = 255, message = "{employee.email.size}")
    private String email;

    @NotBlank(message = "{employee.role.notBlank}")
    @Size(max = 255, message = "{employee.role.size}")
    private String role;

    @NotBlank(message = "{employee.department.notBlank}")
    @Size(max = 255, message = "{employee.department.size}")
    private String department;

    @NotNull(message = "{employee.status.notNull}")
    private EmployeeStatus status;

    @Size(max = 255, message = "{employee.photoUrl.size}")
    private String photoUrl;

    // GETTERS E SETTERS

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public EmployeeStatus getStatus() {
        return status;
    }

    public void setStatus(EmployeeStatus status) {
        this.status = status;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }
}
