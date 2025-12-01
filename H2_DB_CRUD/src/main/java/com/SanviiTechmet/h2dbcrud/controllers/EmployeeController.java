package com.SanviiTechmet.h2dbcrud.controllers;

import com.SanviiTechmet.h2dbcrud.DTOs.*;
import com.SanviiTechmet.h2dbcrud.Entities.Employee;
import com.SanviiTechmet.h2dbcrud.Services.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("api/emp")
@Tag(name = "Employee Controller v1", description = "CRUD Operations Related to Employee")
public class EmployeeController {

    private final EmployeeService service;
    private final ModelMapper mapper;

    @Autowired
    public EmployeeController(EmployeeService service, ModelMapper mapper) {
        this.service = service;
        this.mapper = mapper;

    }

     private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);


    @PostMapping("/create")
    @Operation(summary = "Save Employee in DB")
    public ResponseEntity<ResponseDTO> saveEmployee(@Valid @RequestBody EmployeeRequestDTO dto) {
        logger.info("Saving Employee {}", dto);
        Employee emp = new Employee();
        mapper.map(dto, emp);
        Employee savedEmp = service.createEmployee(emp);
        logger.info("created Employee {}", savedEmp);
        if (savedEmp != null) {
            ResponseDTO responseDTO = new ResponseDTO();
            responseDTO.setMessage("Employee with Employee ID " + savedEmp.getId() + " is Created...");
            logger.info("returning created Employee {}", savedEmp);
            return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
        } else {
            ResponseDTO responseDTO = new ResponseDTO();
            responseDTO.setMessage("Employee is not created due to internal error");
            logger.error("created Employee is Null");
            return new ResponseEntity<>(responseDTO, HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("/get")
    @Operation(summary = "Get All Employees")
    public ResponseEntity<List<EmployeeResponseDTO>> getAllEmployees() {
        return new ResponseEntity<>(service.getAllEmployee()
                .stream()
                .map(emp -> mapper.map(emp, EmployeeResponseDTO.class)
                ).toList(), HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    @Operation(summary = "Get Employee by ID")
    public ResponseEntity<EmployeeResponseDTO> getEmployeeById(@PathVariable Long id) {
        logger.info("getting employee by id {}", id);
        Employee emp = service.getEmployeeById(id);
        EmployeeResponseDTO responseDTO = new EmployeeResponseDTO();
        mapper.map(emp, responseDTO);
        logger.info("returning employee with id {}", id);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Employee by ID")
    public ResponseEntity<ResponseDTO> deleteEmployeeById(@PathVariable Long id) {
        logger.info("deleting employee by id {}", id);
        boolean flag = service.deleteEmployee(id);
        ResponseDTO responseDTO = new ResponseDTO();
        if (flag) {
            responseDTO.setMessage("Employee with Employee ID " + id + " is Deleted Successfully...");
            logger.info("employee deleted successfully");
        }
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @PutMapping("/update")
    @Operation(summary = "Update Salary of Employee")
    public ResponseEntity<EmployeeResponseDTO> updateEmployee(@RequestBody @Valid EmployeeUpdateRequestDTO dto) {
        Employee emp = new Employee();
        mapper.map(dto, emp);
        Employee updatedEmp = service.updateEmployee(emp);
        EmployeeResponseDTO responseDTO = new EmployeeResponseDTO();
        mapper.map(updatedEmp, responseDTO);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @PatchMapping("/")
    @Operation(summary = "Update Employee")
    public ResponseEntity<EmployeeResponseDTO> UpdateEmployeeSalary(@RequestBody EmployeeSalaryUpdateRequestDTO dto) {
        Employee updatedEmp = service.updateEmployeeSalary(dto);
        EmployeeResponseDTO responseDTO = new EmployeeResponseDTO();
        mapper.map(updatedEmp, responseDTO);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }
}
