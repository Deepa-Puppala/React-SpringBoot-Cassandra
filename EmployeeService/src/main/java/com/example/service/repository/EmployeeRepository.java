package com.example.service.repository;

import java.util.List;
import java.util.UUID;

import com.example.service.model.Employee;
import org.springframework.data.cassandra.repository.AllowFiltering;
import org.springframework.data.cassandra.repository.CassandraRepository;

public interface EmployeeRepository extends CassandraRepository<Employee, UUID> {
  @AllowFiltering
  List<Employee> findByName(String name);
  
  List<Employee> findByIndustry(String industry);
}
