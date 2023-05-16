package com.udacity.jdnd.course3.critter.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.DayOfWeek;
import java.util.List;
@Repository
public interface EmployeeRepo extends JpaRepository<Employee, Long> {
        List<Employee> findAllByDaysAvailableContaining(DayOfWeek dayOfWeek);
}
