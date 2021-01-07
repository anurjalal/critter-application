package com.udacity.jdnd.course3.critter.controller;

import com.udacity.jdnd.course3.critter.dto.CustomerDTO;
import com.udacity.jdnd.course3.critter.dto.EmployeeDTO;
import com.udacity.jdnd.course3.critter.dto.EmployeeRequestDTO;
import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.service.CustomerService;
import com.udacity.jdnd.course3.critter.service.EmployeeService;
import com.udacity.jdnd.course3.critter.service.PetService;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Handles web requests related to Users.
 * <p>
 * Includes requests for both customers and employees. Splitting this into separate user and customer controllers
 * would be fine too, though that is not part of the required scope for this class.
 */
@RestController
@RequestMapping("/user")
public class UserController {

    private final EmployeeService employeeService;
    private final CustomerService customerService;
    private final PetService petService;
    private final ModelMapper modelMapper;

    public UserController(EmployeeService employeeService, CustomerService customerService, PetService petService, ModelMapper modelMapper) {
        this.employeeService = employeeService;
        this.customerService = customerService;
        this.petService = petService;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/customer")
    public CustomerDTO saveCustomer(@Valid @RequestBody CustomerDTO customerDTO) {
        Customer customer = convertCustomerDTOToCustomer(customerDTO);
        Customer updated = customerService.saveCustomer(customer);
        return convertCustomerToCustomerDTO(updated);
    }

    @GetMapping("/customer")
    public List<CustomerDTO> getAllCustomers() {
        List<Customer> customers = customerService.getAllCustomers();
        List<CustomerDTO> customerDTOs = new ArrayList<>();
        customers.forEach(customer -> customerDTOs.add(convertCustomerToCustomerDTO(customer)));
        return customerDTOs;
    }

    @GetMapping("/customer/pet/{petId}")
    public CustomerDTO getOwnerByPet(@PathVariable Long petId) {
        Customer customer = customerService.getCustomerByPet(petId).orElseThrow(RuntimeException::new);
        return convertCustomerToCustomerDTO(customer);
    }

    @PostMapping("/employee")
    public EmployeeDTO saveEmployee(@Valid @RequestBody EmployeeDTO employeeDTO) {
        Employee emp = convertEmployeeDTOToEmployee(employeeDTO);
        Employee updated = employeeService.saveEmployee(emp);
        return convertEmployeeToEmployeeDTO(updated);
    }

    //change from POST to GET
    @GetMapping("/employee/{employeeId}")
    public EmployeeDTO getEmployee(@PathVariable Long employeeId) {
        Employee emp = employeeService.getEmployee(employeeId).orElseThrow(RuntimeException::new);
        return convertEmployeeToEmployeeDTO(emp);
    }

    @PutMapping("/employee/{employeeId}")
    public void setAvailability(@RequestBody Set<DayOfWeek> daysAvailable, @PathVariable Long employeeId) {
        employeeService.updateEmployeeAvailability(daysAvailable, employeeId);
    }

    @GetMapping("/employee/availability")
    public List<EmployeeDTO> findEmployeesForService(@Valid @RequestBody EmployeeRequestDTO employeeDTO) {
        List<Employee> employees = employeeService.getEmployeeForService(employeeDTO.getSkills(), employeeDTO.getDate().getDayOfWeek());
        List<EmployeeDTO> employeeDTOs = new ArrayList<>();
        employees.forEach(emp -> employeeDTOs.add(convertEmployeeToEmployeeDTO(emp)));
        return employeeDTOs;
    }

    private EmployeeDTO convertEmployeeToEmployeeDTO(Employee employee) {
        EmployeeDTO employeeDTO = modelMapper.map(employee, EmployeeDTO.class);
        employeeDTO.setDaysAvailable(employee.getDaysAvailable().orElse(null));
        employee.getDaysAvailable().ifPresent(employeeDTO::setDaysAvailable);
        return employeeDTO;
    }

    private Employee convertEmployeeDTOToEmployee(EmployeeDTO employeeDTO) {
        Employee employee = modelMapper.map(employeeDTO, Employee.class);
        if (employeeDTO.getDaysAvailable() != null) {
            employee.setDaysAvailable(employeeDTO.getDaysAvailable());
        }
        return employee;
    }

    private CustomerDTO convertCustomerToCustomerDTO(Customer customer) {
        CustomerDTO customerDTO = modelMapper.map(customer, CustomerDTO.class);
        customerDTO.setNotes(customer.getNotes().orElse(null));
        List<Long> petIds = new ArrayList<>();
        customer.getPet().ifPresent(value -> value.forEach(pet -> petIds.add(pet.getId())));
        customerDTO.setPetIds(petIds);
        return customerDTO;
    }

    private Customer convertCustomerDTOToCustomer(CustomerDTO customerDTO) {
        Customer customer = modelMapper.map(customerDTO, Customer.class);
        List<Pet> pet = new ArrayList<>();
        Optional.ofNullable(customerDTO.getPetIds()).ifPresent(pets -> pets.forEach(it -> {
            petService.getPet(it).ifPresent(pet::add);
        }));
        customer.setPet(pet);
        return customer;
    }
}
