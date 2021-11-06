package com.udacity.jdnd.course3.critter.services;

import com.udacity.jdnd.course3.critter.entities.Customer;
import com.udacity.jdnd.course3.critter.entities.Pet;
import com.udacity.jdnd.course3.critter.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class CustomerService {

    @Autowired
    CustomerRepository customerRepository;


    public Customer saveCustomer(Customer customer) {
        customerRepository.save(customer);
        return customer;
    }

    public Customer getCustomerById(Long id) {
        Customer customer = customerRepository.findById(id).get();
        return customer;
    }


    public List<Customer> getAllCustomers() {
        List<Customer> customerList = customerRepository.findAll();
        return customerList;
    }

    public void addPetToCustomer(Long customerId, Pet pet) {

        // retrieve the customer from the repository
        Customer customerToUpdate = customerRepository.findById(customerId).get();

        // extract the pet list
        List<Pet> customerPetListToUpdate = customerToUpdate.getOwnedPets();

        // update the pet list with the new pet
        if (customerToUpdate.getOwnedPets() == null) {
            customerPetListToUpdate = new ArrayList<>();
            customerPetListToUpdate.add(pet);
        } else {
            customerPetListToUpdate.add(pet);
        }

        // update the customer's petlist
        customerToUpdate.setOwnedPets(customerPetListToUpdate);

        // save customer back
        customerRepository.save(customerToUpdate);
    }

}