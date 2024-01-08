package org.springbootelastic.service;


import org.springbootelastic.model.Employee;
import org.springbootelastic.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public Employee createEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    public Iterable<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public Employee updateEmployee(String id, Employee employee) {

        Optional<Employee> optionalEmployee = employeeRepository.findById(id);

        if(optionalEmployee.isPresent()) {
         Employee oldEmployee = optionalEmployee.get();
         oldEmployee.setName(employee.getName());
         oldEmployee.setDepartment(employee.getDepartment());
         oldEmployee.setSalary(employee.getSalary());

         return employeeRepository.save(oldEmployee);
        }
        return  null;

    }

    public boolean deleteEmployee(String id) {

        Optional<Employee> optionalEmployee = employeeRepository.findById(id);
        if(optionalEmployee.isPresent()) {
            employeeRepository.delete(optionalEmployee.get());
            return true;
        }
        return false;
    }

    public Employee getEmployeeById(String id) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);
        return optionalEmployee.orElse(null);
    }

    public List<Employee> findByName(String name) {
        return employeeRepository.findByName(name);
    }

    public Page<Employee> paginateEmployees(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return employeeRepository.findAll(pageable);
    }

    public List<Employee> filterEmployeeBySalaryRange(double min, double max) {
        return employeeRepository.findBySalaryBetween(min, max);
    }

    public List<Employee> searchEmployeeByNameMatch(String name) {
         return this.employeeRepository.findByNameMatchQuery(name);
    }

    public List<Employee> searchByNameAndSalaryRange(String name, double salary) {
        return this.employeeRepository.findByMatchNameAndSalaryRange(name, salary);
    }

}
