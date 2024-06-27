package com.example.service.model;

import java.util.UUID;

import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Table
public class Employee {
  @PrimaryKey
  private UUID id;

  private String name;
  private String industry;
  private String employeetype;

  public Employee() {

  }

  public Employee(UUID id, String name, String industry, String employeetype) {
    this.id = id;
    this.name = name;
    this.industry = industry;
    this.employeetype = employeetype;
  }

  @Override
  public String toString() {
    return "Employee{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", industry='" + industry + '\'' +
            ", employeetype='" + employeetype + '\'' +
            '}';
  }

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getIndustry() {
    return industry;
  }

  public void setIndustry(String industry) {
    this.industry = industry;
  }

  public String getEmployeetype() {
    return employeetype;
  }

  public void setEmployeetype(String employeetype) {
    this.employeetype = employeetype;
  }
}
