package com.SanviiTechmet.h2dbcrud.DTOs;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

@Data
@Validated
public class ResponseDTO {
    @NotBlank
    private String message;
}
