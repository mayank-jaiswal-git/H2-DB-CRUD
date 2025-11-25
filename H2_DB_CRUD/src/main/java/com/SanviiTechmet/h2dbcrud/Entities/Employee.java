package com.SanviiTechmet.h2dbcrud.Entities;

import com.opencsv.bean.CsvBindByName;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.io.Serializable;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Employee implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;
    @Column
    @CsvBindByName
   private String name;
    @Column
    @CsvBindByName
    private String email;
    @Column
    @CsvBindByName
    private String contactNo;
    @Column
    @CsvBindByName
   private String department;
    @Column
    @CsvBindByName
   private Double salary;
}