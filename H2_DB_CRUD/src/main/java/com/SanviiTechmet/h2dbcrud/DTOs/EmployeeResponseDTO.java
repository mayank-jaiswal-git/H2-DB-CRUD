package com.SanviiTechmet.h2dbcrud.DTOs;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Validated
public class EmployeeResponseDTO {
    @NotNull
    private Long id;
    @NotBlank
    private String name;
    @Email
    private String email;
    @NotBlank
    private String contactNo;
    @NotBlank
    private String department;
    @NotNull
    @Positive
    private double salary;
}




