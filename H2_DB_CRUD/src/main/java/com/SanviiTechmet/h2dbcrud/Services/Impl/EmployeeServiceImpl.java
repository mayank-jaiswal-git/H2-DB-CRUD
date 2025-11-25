package com.SanviiTechmet.h2dbcrud.Services.Impl;

import com.SanviiTechmet.h2dbcrud.DTOs.EmployeeSalaryUpdateRequestDTO;
import com.SanviiTechmet.h2dbcrud.Entities.Employee;
import com.SanviiTechmet.h2dbcrud.Repositories.EmployeeRepo;
import com.SanviiTechmet.h2dbcrud.Services.EmployeeService;
import com.SanviiTechmet.h2dbcrud.exceptions.ResourceNotFoundException;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Data
@NoArgsConstructor
@Service
public class EmployeeServiceImpl implements EmployeeService {


    private EmployeeRepo repo;
    private ModelMapper mapper;


    public EmployeeServiceImpl(EmployeeRepo repo, ModelMapper mapper) {
        this.repo = repo;
        this.mapper = mapper;
    }


    private static final Logger logger = LoggerFactory.getLogger(EmployeeServiceImpl.class);


    @Override
    public Employee createEmployee(Employee emp) {
        logger.info("creating employee {}", emp);
        return repo.save(emp);
    }

    @Override
    public List<Employee> getAllEmployee() {
        List<Employee> employeeList = repo.findAll();
        if (employeeList.isEmpty()) {
            logger.error("Database is empty");
            throw new ResourceNotFoundException("Not Found any Employee in Database");
        } else {
            logger.info("returning All Employees");
            return employeeList;
        }

    }

    @Override
    public Employee getEmployeeById(Long id) {
        logger.info("finding user by id {}", id);
        Optional<Employee> savedEmp = repo.findById(id);
        if (savedEmp.isPresent()) {
            logger.info("returning saved employee {}", savedEmp.get());
            return savedEmp.get();
        } else {
            throw new ResourceNotFoundException("Employee with ID " + id + " is not found");
        }
    }


    @Override
    public boolean deleteEmployee(Long id) {
        logger.info("finding user by id {} for deleting", id);
        Optional<Employee> savedEmp = repo.findById(id);
        if (savedEmp.isPresent()) {
            repo.deleteById(id);
            return true;
        } else {
            throw new ResourceNotFoundException("Employee with ID " + id + " is not found");
        }
    }

    @Override
    public Employee updateEmployee(Employee emp) {
        Optional<Employee> validEmp = repo.findById(emp.getId());
        if (validEmp.isPresent()) {
            Employee getEmp = validEmp.get();
            mapper.map(emp, getEmp);
            return repo.save(getEmp);
        } else {
            throw new ResourceNotFoundException("Employee with ID " + emp.getId() + " is not found");
        }
    }

    @Override
    public Employee updateEmployeeSalary(EmployeeSalaryUpdateRequestDTO dto) {
        Optional<Employee> validEmp = repo.findById(dto.getId());
        if (validEmp.isPresent()) {
            Employee emp = validEmp.get();
            mapper.map(dto, Employee.class);
            return repo.save(emp);
        } else {
            throw new ResourceNotFoundException("Employee with ID " + dto.getId() + " is not found");
        }
    }

    private String privateEmployeeMethod(String str) {
        return "Hello " + str + " Private Method Called";
    }

    public String exceptionThrownMethod(String s) {
        if (s != null) {
            return "Hello" + s;
        } else {
            throw new RuntimeException();

        }
    }
}
