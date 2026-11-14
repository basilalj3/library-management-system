package project.web;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import project.model.UserRole;

/*
 JSON body for POST /api/users, e.g.
 {"role": "STUDENT", "id": "S002", "name": "Sara", "departmentOrFaculty": "IT"}
 */
public record UserRequest(
        @NotNull UserRole role,
        @NotBlank String id,
        @NotBlank String name,
        String departmentOrFaculty) {
}
