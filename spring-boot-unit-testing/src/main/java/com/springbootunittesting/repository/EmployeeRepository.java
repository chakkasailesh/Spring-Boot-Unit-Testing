package com.springbootunittesting.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.springbootunittesting.entity.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
	Optional<Employee> findByEmail(String email);

	@Query("select e from Employee e where e.firstName = ?1 and e.lastName = ?2")
	Employee findEmployee(String firstName, String LastName);
}
