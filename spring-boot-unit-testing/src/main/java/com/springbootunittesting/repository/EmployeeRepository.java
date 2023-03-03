package com.springbootunittesting.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.springbootunittesting.entity.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
	Optional<Employee> findByEmail(String email);

	@Query(value = "select * from employees e where e.first_name = ?1 and e.last_name = ?2", nativeQuery = true)
	Employee findEmployee(String firstName, String lastName);

	@Query("select e from Employee e where e.firstName = :firstName and e.email = :email")
	Employee findEmployeeByFirstNameAndEmail(@Param("firstName") String firstName, @Param("email") String email);
}
