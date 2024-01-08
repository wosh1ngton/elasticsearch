package org.springbootelastic.repository;

import org.springbootelastic.model.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends ElasticsearchRepository<Employee, String> {

    List<Employee> findByName(String name);
    Page<Employee> findAll(Pageable pageable);
    List<Employee> findBySalaryBetween(double min, double max);

    @Query(value = "{\"bool\": { \"must\": [{ \"match\": { \"name\": \"?0\" } } ] }  }")
    List<Employee> findByNameMatchQuery(String name);

    @Query(value = "{\"bool\": {\"must\": [{\"match\": {\"name\": \"?0\"}},{\"range\": {\"salary\": {\"gte\": ?1}}}]}}")
    List<Employee> findByMatchNameAndSalaryRange(String name, double minSalary);
}
