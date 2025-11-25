package com.SanviiTechmet.h2dbcrud.DTOs;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EmployeeRequestDTO {

    @NotBlank(message = "Name is required")
    @Size(min = 2, max = 20, message = "Name must be of min 2 character and max 20 character")
    @Schema(description = "Name is required...")
    private String name;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid Email Format")
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$",message = "Email address is Invalid")
    private String email;

    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^[6-9]\\d{9}$", message = "Phone number must be 10 digits and it starts from 6 to 9")
    private String contactNo;

    @NotBlank(message = "Department is required")
    @Size(min = 2, max = 20, message = "Department must be of min 2 character and max 20 character")
    private String department;

    @NotNull(message = "Salary cannot ne null")
    @Positive(message = "Salary must be greater than zero")
    private double salary;
}
