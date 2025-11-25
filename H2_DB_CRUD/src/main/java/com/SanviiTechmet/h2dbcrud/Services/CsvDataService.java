package com.SanviiTechmet.h2dbcrud.Services;

import com.SanviiTechmet.h2dbcrud.Entities.Employee;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CsvDataService {

    public void saveDataFromCSV(MultipartFile file);

    public List<Employee> getAllCSVData();
}
