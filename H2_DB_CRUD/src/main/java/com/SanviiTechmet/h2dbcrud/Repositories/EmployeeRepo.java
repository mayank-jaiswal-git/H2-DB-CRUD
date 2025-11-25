package com.SanviiTechmet.h2dbcrud.Repositories;

import com.SanviiTechmet.h2dbcrud.Entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepo extends JpaRepository<Employee, Long> {
}
