package com.udacity.jdnd.course3.critter.user;

import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.pet.PetService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jdbc.core.JdbcAggregateOperations;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Users.
 *
 * Includes requests for both customers and employees. Splitting this into separate user and customer controllers
 * would be fine too, though that is not part of the required scope for this class.
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private CustomerService customerService;
    @Autowired
    private  EmployeeService employeeService;
    @Autowired
    private PetService petService;

    @PostMapping("/customer")
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO){
        Customer customer = convertCustomerDTOToCustomer(customerDTO);
        Customer savedCustomer = customerService.saveCustomer(customer);
        customerDTO.setId(savedCustomer.getId());
        return customerDTO;
    }

    private Customer convertCustomerDTOToCustomer(CustomerDTO customerDTO) {
        Customer customer = new Customer();
        BeanUtils.copyProperties(customerDTO, customer);
        return customer;
    }

    @GetMapping("/customer")
    public List<CustomerDTO> getAllCustomers(){
        List<Customer> customers = customerService.getAllCustomers();
        return customers.stream().map(c->convertCustomerToCustomerDTO(c)).collect(Collectors.toList());
    }

    private CustomerDTO convertCustomerToCustomerDTO(Customer c) {
        CustomerDTO customerDTO = new CustomerDTO();
        if(c != null) {
            BeanUtils.copyProperties(c,customerDTO);
            List<Long> petIds = new ArrayList<>();
            if (c.getPets()!=null) {
                petIds = c.getPets().stream().map(Pet::getId).collect(Collectors.toList());
            }
            customerDTO.setPetIds(petIds);
            return customerDTO;
        }
        return null;
    }

    @GetMapping("/customer/pet/{petId}")
    public CustomerDTO getOwnerByPet(@PathVariable long petId){
        return convertCustomerToCustomerDTO(customerService.getCustomerByPetId(petId));
        
    }

    @PostMapping("/employee")
    public EmployeeDTO saveEmployee(@RequestBody EmployeeDTO employeeDTO) {
        Employee employee = convertEmployeeDTOToEmployee(employeeDTO);
        Employee savedEmployee = employeeService.saveEmployee(employee);
        employeeDTO.setId(savedEmployee.getId());
        return employeeDTO;
    }

    private Employee convertEmployeeDTOToEmployee(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeDTO, employee);
        return employee;
    }

    @PostMapping("/employee/{employeeId}")
    public EmployeeDTO getEmployee(@PathVariable long employeeId) {
        return convertEmployeeToEmployeeDTO(employeeService.getEmployee(employeeId));
    }
    private EmployeeDTO convertEmployeeToEmployeeDTO(Employee employee){
        EmployeeDTO employeeDTO = new EmployeeDTO();
        BeanUtils.copyProperties(employee, employeeDTO);
        return employeeDTO;
    }
    @PutMapping("/employee/{employeeId}")
    public void setAvailability(@RequestBody Set<DayOfWeek> daysAvailable, @PathVariable long employeeId) {
        employeeService.setAvailability(daysAvailable,employeeId);
    }

    @GetMapping("/employee/availability")
    public List<EmployeeDTO> findEmployeesForService(@RequestBody EmployeeRequestDTO employeeDTO) {
        List<Employee> employees = employeeService.findEmployeesForService(employeeDTO.getDate(),employeeDTO.getSkills());
        return employees.stream().map(this::convertEmployeeToEmployeeDTO).collect(Collectors.toList());    }

}
