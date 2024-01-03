package org.rean.employee.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@NotNull
@NotEmpty
@NotBlank
public class EmployeeRequest {
    @NotNull
    @NotEmpty
    @NotBlank
    private String firstName;
    private String lastName;
    private String email;
}
