package com.SanviiTechmet.h2dbcrud.Services;

import com.SanviiTechmet.h2dbcrud.DTOs.EmployeeSalaryUpdateRequestDTO;
import com.SanviiTechmet.h2dbcrud.Entities.Employee;

import java.util.List;

public interface EmployeeService {

    public Employee createEmployee(Employee emp);

    public List<Employee> getAllEmployee();

    public Employee getEmployeeById(Long id);

    public boolean deleteEmployee(Long id);

    public Employee updateEmployee(Employee emp);

    public Employee updateEmployeeSalary(EmployeeSalaryUpdateRequestDTO dto);
}
