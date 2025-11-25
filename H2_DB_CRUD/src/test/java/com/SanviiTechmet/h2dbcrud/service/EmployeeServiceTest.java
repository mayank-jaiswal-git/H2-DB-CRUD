package com.SanviiTechmet.h2dbcrud.service;

import com.SanviiTechmet.h2dbcrud.DTOs.EmployeeSalaryUpdateRequestDTO;
import com.SanviiTechmet.h2dbcrud.Entities.Employee;
import com.SanviiTechmet.h2dbcrud.Repositories.EmployeeRepo;
import com.SanviiTechmet.h2dbcrud.Services.Impl.EmployeeServiceImpl;
import com.SanviiTechmet.h2dbcrud.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {
    @Mock
    private EmployeeRepo repo;

    @InjectMocks
    private EmployeeServiceImpl service;




    @BeforeAll
    public static void beforeAll(){
        System.out.println("BeforeAll Called");
    }

    @BeforeEach
    void setUp(){
        ModelMapper mapper;
        System.out.println("BeforeEach Called");
        mapper = new ModelMapper();
        service.setMapper(mapper);
    }

    @Test
    void testCreateEmployee(){
        Employee emp = new
                Employee(1L, "Amit Singh",
                "amitsingh@gmail,com",
                "799907349",
                "Technical",
                7000D);

        when(repo.save(emp)).thenReturn(emp);

        Employee actualEmp = service.createEmployee(emp);


        assertNotNull(actualEmp);
        assertEquals(emp.getId(),actualEmp.getId());
        assertEquals(emp.getName(),actualEmp.getName());
        assertEquals(emp.getDepartment(),actualEmp.getDepartment());
        assertEquals(emp.getSalary(),actualEmp.getSalary());
        assertEquals(1L, (long) actualEmp.getId());

        verify(repo).save(emp);
    }

    @Test
    void testCreateEmployee_Negative_whenRepoThrowsException(){
        Employee emp = new
                Employee(1L, "Amit Singh",
                "amitsingh@gmail,com",
                "799907349",
                "Technical",
                7000D);

        when(repo.save(emp)).thenThrow(new RuntimeException("DB Error"));

        assertThrows(RuntimeException.class, () -> service.createEmployee(emp));

        verify(repo).save(emp);
    }



    @AfterEach
    void afterEach(){
        System.out.println("AfterEach Called");
    }

    @Test
    void testGetAllEmployee(){
      List<Employee> mockEmployees = Arrays.asList(
              new Employee(1L, "Amit Singh","amit@gmail.com","6987456321", "Technical", 7000D),
              new Employee(2L, "Mayank Jaiswal","mayank@gmail.com","7845963214", "Technical", 6500D),
              new Employee(3L, "Priyanka Sharma","priyanka@gmail.com","8974563214", "HR", 30000D)
      );

      when(repo.findAll()).thenReturn(mockEmployees);

      List<Employee> actualEmployees = service.getAllEmployee();

      assertNotNull(actualEmployees);
      assertEquals(3, actualEmployees.size());
      assertEquals("Mayank Jaiswal", actualEmployees.get(1).getName());

      verify(repo,times(1)).findAll();
    }

    @Test
    void testGetAllEmployee_Negative_whenEmptyDatabase(){
        when(repo.findAll()).thenReturn(Collections.emptyList());

        assertThrows(ResourceNotFoundException.class, () -> service.getAllEmployee());

        verify(repo, times(1)).findAll();
    }

    @Test
    void testDeleteEmployee(){
        assertThrows(ResourceNotFoundException.class,()->{
//            doNothing().when(repo).deleteById(1L);
            service.deleteEmployee(1L);
            verify(repo,times(1)).deleteById(1L);
        });

    }

    @Test
    void testDeleteEmployee_Negative_whenNotFound(){
        when(repo.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.deleteEmployee(1L));

        verify(repo, times(1)).findById(1L);
    }

    @Test
    void testUpdateEmployee(){
        Optional<Employee> empOp = Optional.of(
                new Employee(2L, "Mayank Jaiswal","mayank@gmail.com","7845963214", "IT Support",8500D));
        Employee emp = new Employee(2L, "Mayank Jaiswal","mayank@gmail.com","7845963214", "IT Support",8500D);

        when(repo.findById(emp.getId())).thenReturn(empOp);

        when(repo.save(emp)).thenReturn(emp);

        Employee actualUpdatedEmp = service.updateEmployee(emp);

        assertEquals(empOp.get(), actualUpdatedEmp);
        assertEquals(emp.getName(), actualUpdatedEmp.getName());
        assertEquals(emp.getDepartment(), actualUpdatedEmp.getDepartment());
        assertEquals(emp.getSalary(), actualUpdatedEmp.getSalary());

        verify(repo, times(1)).findById(2L);

        verify(repo, times(1)).save(emp);

    }

    @Test
    void testUpdateEmployee_Negative_whenNotFound(){
        Employee emp = new Employee(10L, "John Doe","john@doe.com","9876543210","IT", 5000D);
        when(repo.findById(emp.getId())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.updateEmployee(emp));

        verify(repo, times(1)).findById(emp.getId());
    }

    @Test
    void testPartiallyUpdateEmployee(){
        Optional<Employee> empOp = Optional.of(
                new Employee(3L, "Priyanka Sharma","priyanka@gmail.com","9874563212", "HR", 30000D));
        Employee emp = new Employee(3L, "Priyanka Sharma","priyanka@gmail.com","9874563212","HR", 30000D);
        EmployeeSalaryUpdateRequestDTO dto = new EmployeeSalaryUpdateRequestDTO(3L, 30000);

        when(repo.findById(3L)).thenReturn(empOp);

        when(repo.save(emp)).thenReturn(emp);

        Employee actualPartialUpdateEmp = service.updateEmployeeSalary(dto);

        assertEquals(emp, actualPartialUpdateEmp);
        assertEquals(dto.getId(), actualPartialUpdateEmp.getId());
        assertEquals(dto.getSalary(),actualPartialUpdateEmp.getSalary());

        verify(repo, times(1)).save(emp);

        verify(repo, times(1)).findById(3L);

    }

    @Test
    void testPartiallyUpdateEmployee_Negative_whenNotFound(){
        EmployeeSalaryUpdateRequestDTO dto = new EmployeeSalaryUpdateRequestDTO(99L, 30000D);
        when(repo.findById(dto.getId())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.updateEmployeeSalary(dto));

        verify(repo, times(1)).findById(dto.getId());
    }

    @Test
    void testPrivateMethod() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method PrivateEmployeeMethod = EmployeeServiceImpl.class.getDeclaredMethod("privateEmployeeMethod", String.class);
        PrivateEmployeeMethod.setAccessible(true);
        EmployeeServiceImpl employeeService = new EmployeeServiceImpl();
       String str = (String) PrivateEmployeeMethod.invoke(employeeService,"Mayank");
       assertNotEquals(null, str);
    }

    @Test
    void testRuntimeException(){
        assertThrows(RuntimeException.class,()->{
            String output = service.exceptionThrownMethod(null);
        });
    }

    @AfterAll
    public static void afterAll(){
        System.out.println("AfterAll Called");
    }


}