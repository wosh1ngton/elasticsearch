package org.springbootelastic.controller;

import co.elastic.clients.elasticsearch.nodes.Http;
import org.springbootelastic.model.Employee;
import org.springbootelastic.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping
    public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee) {
        Employee savedEmployee = employeeService.createEmployee(employee);
        return new ResponseEntity<>(savedEmployee, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Iterable<Employee>> getAllEmployees() {
        Iterable<Employee> allEmployees = employeeService.getAllEmployees();
        return new ResponseEntity<>(allEmployees, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable String id) {

        Employee employeeById = employeeService.getEmployeeById(id);

        if(employeeById != null) {
            return new ResponseEntity<>(employeeById,HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable String id, @RequestBody Employee employee) {

        Employee updateEmployee = employeeService.updateEmployee(id, employee);

        if(updateEmployee != null) {
            return new ResponseEntity<>(updateEmployee,HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeEmployee(@PathVariable String id) {

        Employee employeeById = employeeService.getEmployeeById(id);

        if(employeeById != null) {
            employeeService.deleteEmployee(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/search")
    public ResponseEntity<List<Employee>> searchEmployeeByName(@RequestParam String name) {

        List<Employee> employees = employeeService.findByName(name);
        return new ResponseEntity<>(employees,HttpStatus.OK);
    }

    @GetMapping("/page")
    public ResponseEntity<Page<Employee>> paginateEmployees(@RequestParam int page, @RequestParam int size) {
        Page<Employee> employees = employeeService.paginateEmployees(page, size);
        return  new ResponseEntity<>(employees, HttpStatus.OK);
    }

    @GetMapping("/filter")
    public ResponseEntity<List<Employee>> filterEmployeesBySalaryRange(@RequestParam int min, @RequestParam int max) {
        List<Employee> employees = employeeService.filterEmployeeBySalaryRange(min, max);
        return  new ResponseEntity<>(employees, HttpStatus.OK);
    }

    @GetMapping("/search/match")
    public ResponseEntity<List<Employee>> searchByNameMatchQuery(@RequestParam String name) {
        List<Employee> employees = employeeService.searchEmployeeByNameMatch(name);
        return  new ResponseEntity<>(employees, HttpStatus.OK);
    }

    @GetMapping("/search/match-range")
    public ResponseEntity<List<Employee>> searchByNameAndSalaryRange(@RequestParam String name, @RequestParam double salary) {
        List<Employee> employees = employeeService.searchByNameAndSalaryRange(name, salary);
        return  new ResponseEntity<>(employees, HttpStatus.OK);
    }

}
