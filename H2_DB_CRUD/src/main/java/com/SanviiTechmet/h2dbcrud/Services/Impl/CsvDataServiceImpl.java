package com.SanviiTechmet.h2dbcrud.Services.Impl;

import com.SanviiTechmet.h2dbcrud.Entities.Employee;
import com.SanviiTechmet.h2dbcrud.Repositories.EmployeeRepo;
import com.SanviiTechmet.h2dbcrud.Services.CsvDataService;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;

import java.util.List;
@Service
public class CsvDataServiceImpl implements CsvDataService {

    @Autowired
    private EmployeeRepo employeeRepo;

    @Override
    public void saveDataFromCSV(MultipartFile csvFile) {
        try (Reader reader = new InputStreamReader(csvFile.getInputStream(), StandardCharsets.UTF_8)){

            //Parse CSV into Employee Object
            CsvToBean<Employee> csvToBean = new CsvToBeanBuilder<Employee>(reader)
                    .withType(Employee.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .withIgnoreEmptyLine(true)
                    .build();

            List<Employee> employees = csvToBean.stream().toList();

            employeeRepo.saveAll(employees);
        }
        catch (Exception e) {
                throw new RuntimeException("Error processing CSV file: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Employee> getAllCSVData() {
        return employeeRepo.findAll();
    }


}
