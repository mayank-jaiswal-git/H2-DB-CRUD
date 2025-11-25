package com.SanviiTechmet.h2dbcrud.controller;

import com.SanviiTechmet.h2dbcrud.controllers.EmployeeController;
import com.SanviiTechmet.h2dbcrud.DTOs.EmployeeRequestDTO;
import com.SanviiTechmet.h2dbcrud.DTOs.EmployeeResponseDTO;
import com.SanviiTechmet.h2dbcrud.DTOs.EmployeeSalaryUpdateRequestDTO;
import com.SanviiTechmet.h2dbcrud.DTOs.EmployeeUpdateRequestDTO;
import com.SanviiTechmet.h2dbcrud.Entities.Employee;
import com.SanviiTechmet.h2dbcrud.Services.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@WebMvcTest(EmployeeController.class)
class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private EmployeeService service;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testSaveEmployee() throws Exception {
        EmployeeRequestDTO dto = new EmployeeRequestDTO("Amit Singh","amit@gmail.com","8954763214", "Technical",7000D);
        Employee requestEmp = new Employee();
        mapper.map(dto,requestEmp);
        Employee emp = new Employee(1L, "Amit Singh","amit@gmail.com","8954763214", "Technical",7000D);

        when(service.createEmployee(requestEmp)).thenReturn(emp);

        mockMvc.perform(post("/api/emp/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value("Employee with Employee ID 1 is Created..."));
    }

        @Test
        void testSaveEmployee_Negative_whenServiceReturnsNull() throws Exception {
                EmployeeRequestDTO dto = new EmployeeRequestDTO("Amit Singh","amit@gmail.com","8954763214", "Technical",7000D);
                Employee requestEmp = new Employee();
                mapper.map(dto,requestEmp);

                when(service.createEmployee(requestEmp)).thenReturn(null);

                mockMvc.perform(post("/api/emp/create")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(dto)))
                                .andExpect(status().isBadRequest())
                                .andExpect(jsonPath("$.message").value("Employee is not created due to internal error"));
        }


    @Test
    void testGetAllEmployees() throws Exception {
        List<EmployeeResponseDTO> dtoList = Arrays.asList(
                new EmployeeResponseDTO(1L,"Amit Singh","amit@gmail.com","8954763214", "Technical",7000D),
                new EmployeeResponseDTO(2L,"Mayank Jaiswal","mayank@gmail.com","9784563214", "IT Support", 6500D),
                new EmployeeResponseDTO(3L, "Priyanka Sharma","priyanka@gmail.com","6987452136", "HR", 30000D)
                );

        when(service.getAllEmployee()).thenReturn(dtoList
                .stream()
                .map(emp -> mapper
                        .map(emp, Employee.class))
                .toList());

        mockMvc.perform(get("/api/emp/get"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Amit Singh"))
                .andExpect(jsonPath("$[1].department").value("IT Support"));

    }

        @Test
        void testSaveEmployee_Negative_whenInvalidDTO() throws Exception {
                // invalid DTO: blank name, invalid email, invalid contactNo, blank department, negative salary
                EmployeeRequestDTO dto = new EmployeeRequestDTO("", "not-an-email", "123", "", -100D);

                mockMvc.perform(post("/api/emp/create")
                                                .contentType(MediaType.APPLICATION_JSON)
                                                .content(objectMapper.writeValueAsString(dto)))
                                .andExpect(status().isBadRequest())
                                .andExpect(jsonPath("$.name").value("Name is required"))
                                .andExpect(jsonPath("$.email").value(anyOf(is("Email address is Invalid"), is("Invalid Email Format"))))
                                .andExpect(jsonPath("$.contactNo").value("Phone number must be 10 digits and it starts from 6 to 9"))
                                .andExpect(jsonPath("$.department").value(anyOf(is("Department is required"), is("Department must be of min 2 character and max 20 character"))))
                                .andExpect(jsonPath("$.salary").value("Salary must be greater than zero"));
        }

        @Test
        void testGetAllEmployees_Negative_whenEmptyDatabase() throws Exception {
                when(service.getAllEmployee()).thenThrow(new com.SanviiTechmet.h2dbcrud.exceptions.ResourceNotFoundException("Not Found any Employee in Database"));

                mockMvc.perform(get("/api/emp/get"))
                                .andExpect(status().isNotFound())
                                .andExpect(content().string("Not Found any Employee in Database"));
        }

    @Test
    void testDeleteEmployeeById() throws Exception {
        long id = 1L;
        when(service.deleteEmployee(id)).thenReturn(true);

        mockMvc.perform(delete("/api/emp/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message")
                        .value("Employee with Employee ID 1 is Deleted Successfully..."));
    }

        @Test
        void testGetEmployeeById_Negative_whenNotFound() throws Exception {
                long id = 1L;
                when(service.getEmployeeById(id)).thenThrow(new com.SanviiTechmet.h2dbcrud.exceptions.ResourceNotFoundException("Employee with ID " + id + " is not found"));

                mockMvc.perform(get("/api/emp/get/1"))
                                .andExpect(status().isNotFound())
                                .andExpect(content().string("Employee with ID 1 is not found"));
        }

        @Test
        void testDeleteEmployeeById_Negative_whenNotFound() throws Exception {
                long id = 1L;
                when(service.deleteEmployee(id)).thenThrow(new com.SanviiTechmet.h2dbcrud.exceptions.ResourceNotFoundException("Employee with ID " + id + " is not found"));

                mockMvc.perform(delete("/api/emp/1"))
                                .andExpect(status().isNotFound())
                                .andExpect(content().string("Employee with ID 1 is not found"));
        }

    @Test
    void testUpdateEmployee() throws Exception {
        Employee emp = new
                Employee(2L, "Mayank Jaiswal","mayank@gmail.com","8974563214","IT Support",8500D);
        EmployeeResponseDTO dto = new
                EmployeeResponseDTO(2L, "Mayank Jaiswal","mayank@gmail.com","8974563214","IT Support", 8500D);

        when(service.updateEmployee(emp)).thenReturn(emp);

        mockMvc.perform(put("/api/emp/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Mayank Jaiswal"))
                .andExpect(jsonPath("$.department").value("IT Support"))
                .andExpect(jsonPath("$.salary").value(8500D));
    }

    @Test
    void testUpdateEmployee_Negative_whenNotFound() throws Exception {
        Employee emp = new
                Employee(2L, "Mayank Jaiswal","mayank@gmail.com","8974563214","IT Support",8500D);
        EmployeeResponseDTO dto = new
                EmployeeResponseDTO(2L, "Mayank Jaiswal","mayank@gmail.com","8974563214","IT Support", 8500D);

        when(service.updateEmployee(emp)).thenThrow(new com.SanviiTechmet.h2dbcrud.exceptions.ResourceNotFoundException("Employee with ID " + emp.getId() + " is not found"));

        mockMvc.perform(put("/api/emp/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Employee with ID 2 is not found"));
    }

        @Test
        void testUpdateEmployee_Negative_whenInvalidDTO() throws Exception {
                // invalid update DTO: id null, blank name, negative salary
                EmployeeUpdateRequestDTO dto = new EmployeeUpdateRequestDTO();
                dto.setId(null);
                dto.setName("");
                dto.setEmail("not-an-email");
                dto.setContactNo("123");
                dto.setDepartment("");
                dto.setSalary(-500d);

                mockMvc.perform(put("/api/emp/update")
                                                .contentType(MediaType.APPLICATION_JSON)
                                                .content(objectMapper.writeValueAsString(dto)))
                                .andExpect(status().isBadRequest())
                                .andExpect(jsonPath("$.id").value("Employee ID is required"))
                                .andExpect(jsonPath("$.name").value(anyOf(is("Name is required"), is("Name must be of min 2 character and max 20 character"))))
                                .andExpect(jsonPath("$.salary").value("Salary must be greater than zero"));
        }

    @Test
    void testPartiallyUpdateEmployee() throws Exception {
        EmployeeSalaryUpdateRequestDTO dto = new EmployeeSalaryUpdateRequestDTO(2L, 8500D);
        Employee emp = new
                Employee(2L, "Mayank Jaiswal","mayank@gmail.com","8974563214", "IT Support", 8500D);

        when(service.updateEmployeeSalary(dto)).thenReturn(emp);

        mockMvc.perform(patch("/api/emp/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Mayank Jaiswal"))
                .andExpect(jsonPath("$.department").value("IT Support"))
                .andExpect(jsonPath("$.salary").value(8500D))
                .andExpect(jsonPath("$.id").value(2L));
    }

        @Test
        void testPartiallyUpdateEmployee_Negative_whenNotFound() throws Exception {
                EmployeeSalaryUpdateRequestDTO dto = new EmployeeSalaryUpdateRequestDTO(2L, 8500D);

                when(service.updateEmployeeSalary(dto)).thenThrow(new com.SanviiTechmet.h2dbcrud.exceptions.ResourceNotFoundException("Employee with ID " + dto.getId() + " is not found"));

                mockMvc.perform(patch("/api/emp/")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(dto)))
                                .andExpect(status().isNotFound())
                                .andExpect(content().string("Employee with ID 2 is not found"));
        }
}