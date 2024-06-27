package com.example.service.controller;

import java.util.*;

import com.example.service.Exception.ResourceNotFoundException;
import com.example.service.model.Employee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.service.repository.EmployeeRepository;
import com.datastax.driver.core.utils.UUIDs;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api")
public class EmployeeController {

  private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);


  @Autowired
  EmployeeRepository employeeRepository;

  @GetMapping("/employees")
  public ResponseEntity<List<Employee>> getAllEmployees() {
    try {
      List<Employee> emp = new ArrayList<Employee>();

        employeeRepository.findAll().forEach(emp::add);

      if (emp.isEmpty()) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
      }

      return new ResponseEntity<>(emp, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping("/employees/{id}")
  public ResponseEntity<Employee> getEmployeebyId(@PathVariable("id") UUID id) {
    Optional<Employee> empData = employeeRepository.findById(id);

      return empData.map(employee -> new ResponseEntity<>(employee, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }

  @PostMapping("/employees")
  public ResponseEntity<Employee> createEmployee(@RequestBody Employee emp) {
    try {
      logger.debug("body ***", emp.getEmployeetype(), emp.getName(), emp.getIndustry());
      Employee _emp = employeeRepository.save(new Employee(UUIDs.timeBased(), emp.getName(),emp.getEmployeetype(),emp.getIndustry()));
      return new ResponseEntity<>(_emp, HttpStatus.CREATED);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @PutMapping("/employees/{id}")
  public ResponseEntity<Employee> updateEmployee(@PathVariable("id") UUID id, @RequestBody Employee employee) {
    Optional<Employee> empData = employeeRepository.findById(id);

    if (empData.isPresent()) {
      Employee e = empData.get();
      e.setName(employee.getName());
      e.setEmployeetype(employee.getEmployeetype());
      e.setIndustry(employee.getIndustry());
      return new ResponseEntity<>(employeeRepository.save(e), HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @DeleteMapping("/employees/{id}")
  public ResponseEntity<Map<String, String>> deleteEmployee(@PathVariable("id") UUID id) {
    try {
      if (!employeeRepository.existsById(id)) {
        throw new ResourceNotFoundException("Employee not found with id " + id);
      }
      employeeRepository.deleteById(id);
      Map<String, String> response = new HashMap<>();
      response.put("message", "Employee Deleted Successfully");
      response.put("Status", "200");
      return ResponseEntity.ok(response);
    } catch (Exception e) {
      Map<String, String> response = new HashMap<>();
      response.put("message", e.getMessage());
      return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);

    }
  }

  @DeleteMapping("/employees")
  public ResponseEntity<HttpStatus> deleteAllEmployees() {
    try {
      employeeRepository.deleteAll();
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

//  @GetMapping("/employees/{industry}")
//  public ResponseEntity<List<Employee>> findByIndustry(@PathVariable("industry") String industry) {
//    try {
//      List<Employee> emp = employeeRepository.findByIndustry(industry);
//
//      if (emp.isEmpty()) {
//        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//      }
//      return new ResponseEntity<>(emp, HttpStatus.OK);
//    } catch (Exception e) {
//      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//    }
//  }

}
