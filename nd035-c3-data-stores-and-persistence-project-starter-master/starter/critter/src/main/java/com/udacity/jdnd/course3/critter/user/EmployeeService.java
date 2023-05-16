package com.udacity.jdnd.course3.critter.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
@Service
public class EmployeeService {
    @Autowired
    EmployeeRepo employeeRepo;

    public Employee saveEmployee(Employee employee) {
        return employeeRepo.save(employee);
    }

    public Employee getEmployee(Long id) {
        return employeeRepo.getOne(id);
    }

    public void setAvailability(Set<DayOfWeek> daysAvailable, long employeeId) {

        Employee employee = employeeRepo.getOne(employeeId);
        employee.setDaysAvailable(daysAvailable);
        employeeRepo.save(employee);
    }

    public List<Employee> findEmployeesForService(LocalDate date, Set<EmployeeSkill> skills) {
        List<Employee> employees = employeeRepo.findAllByDaysAvailableContaining(date.getDayOfWeek()).stream()
                .filter(employee -> employee.getSkills().containsAll(skills))
                .collect(Collectors.toList());
        return employees;
    }

    public List<Employee> findEmployeesByid(List<Long> id){
        return employeeRepo.findAllById(id);
    }
    public void deleteEmployee(Long id){
        employeeRepo.deleteById(id);
    }
}
