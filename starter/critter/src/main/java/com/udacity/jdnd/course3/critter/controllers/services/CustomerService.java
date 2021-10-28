package com.udacity.jdnd.course3.critter.controllers.services;

import com.udacity.jdnd.course3.critter.DTOs.CustomerDTO;
import com.udacity.jdnd.course3.critter.entities.Customer;
import com.udacity.jdnd.course3.critter.repositories.CustomerRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class CustomerService {
    @Autowired
    EntityManager entityManager;

    @Autowired
    CustomerRepository customerRepository;


    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO) {
        Customer customer = convertCustomerDTOToCustomer(customerDTO);

        // persist new customer and flush
        entityManager.persist(customer);
        entityManager.flush();

        // return the customerDTO after proper conversion to
        return convertCustomerToCustomerDTO(customer);
    }


    public List<CustomerDTO> getAllCustomers() {
        List<Customer> customerList = customerRepository.findAll();
        List<CustomerDTO> customerDTOList = new ArrayList<>();

        // get all customers from the repository
        customerRepository.findAll();

        //todo: lambda expression would be a nice improvement to above .findAll() call
        for (Customer customer : customerList) {
            customerDTOList.add(convertCustomerToCustomerDTO(customer));
        }

        return customerDTOList;
    }

    private CustomerDTO convertCustomerToCustomerDTO(Customer customer) {
        CustomerDTO customerDTO = new CustomerDTO();
        BeanUtils.copyProperties(customer, customerDTO);
        return customerDTO;
    }

    private Customer convertCustomerDTOToCustomer(CustomerDTO customerDTO) {
        Customer customer = new Customer();
        BeanUtils.copyProperties(customerDTO, customer);
        return customer;
    }
}