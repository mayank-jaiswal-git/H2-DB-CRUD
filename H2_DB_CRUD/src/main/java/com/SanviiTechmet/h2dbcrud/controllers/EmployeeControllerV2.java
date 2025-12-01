package com.SanviiTechmet.h2dbcrud.controllers;

import com.SanviiTechmet.h2dbcrud.DTOs.EmployeeResponseDTO;
import com.SanviiTechmet.h2dbcrud.Services.CsvDataService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@RestController
@RequestMapping("/api/emp/v2")
@Slf4j
@Tag(name = "Employee Controller v2", description = "SaveAll and FindAll Operations related to Employee with CSV")
public class EmployeeControllerV2 {

    @Autowired
    private CsvDataService csvDataService;
    @Autowired
    private ModelMapper mapper;

    @PostMapping(value = "/upload", consumes = "multipart/form-data")
    @Operation(summary = "Upload Employee Data using C.S.V ")
    public ResponseEntity<String> uploadCSVFile(@RequestParam("csvFile") MultipartFile csvFile){
        log.info("CSV file received {}",csvFile.getOriginalFilename());
        try {
            csvDataService.saveDataFromCSV(csvFile);
            log.info("CSV file processed successfully {}",csvFile.getOriginalFilename());
            return ResponseEntity.ok("CSV Data Uploaded Successfully...");
        } catch (Exception e) {
            log.error("Error uploading CSV file: {}", e.getMessage());
            return ResponseEntity.badRequest().body("Failed to process CSV file: " + e.getMessage());
        }
    }

    @GetMapping("/getAll")
    @Operation(summary = "Get All Employee Data")
    public ResponseEntity<List<EmployeeResponseDTO>> getAllData(){
        log.info("Getting Data from DB...");
        List<EmployeeResponseDTO> responseDTOList = csvDataService.getAllCSVData()
                .stream()
                .map(emp -> mapper.map(emp,EmployeeResponseDTO.class)).toList();
        return new ResponseEntity<>(responseDTOList, HttpStatus.OK);
    }
}
