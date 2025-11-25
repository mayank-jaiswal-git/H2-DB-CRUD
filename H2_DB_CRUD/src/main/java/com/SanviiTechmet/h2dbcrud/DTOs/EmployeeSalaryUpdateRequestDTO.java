package com.SanviiTechmet.h2dbcrud.DTOs;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EmployeeSalaryUpdateRequestDTO {

    @NotNull(message = "Employee ID is required")
    private Long id;
    @NotNull(message = "Salary cannot ne null")
    @Positive(message = "Salary must be greater than zero")
    private double salary;

}
