package br.com.ewave.customerApi.service;

import br.com.ewave.customerApi.model.Address;
import br.com.ewave.customerApi.model.Customer;
import br.com.ewave.customerApi.repository.AddressRepository;
import br.com.ewave.customerApi.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping({"/customers"})
public class CustomerService {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    AddressRepository addressRepository;

    @GetMapping
    public List<Customer> listCustomers(){
       return customerRepository.findAll();
    }

    @GetMapping(path = {"/{id}"})
    public Optional<Customer> findCustomerPorId(@PathVariable("id") long id){
        return customerRepository.findById(id);
    }

    @GetMapping(path = {"findByZipCode/{zipCode}"})
    public List<Customer> findCustomerByZipCode(@PathVariable("zipCode") String zipCode){
        return customerRepository.findCustomerZipCod(zipCode);

    }
    @PutMapping(value="/{id}")
    public ResponseEntity<Customer> editCustomer(@PathVariable("id") long id, @RequestBody Customer customer){
       return customerRepository.findById(id).map(x -> {
          x.setName(customer.getName());
          x.setRegistrationDate(customer.getRegistrationDate());
          x.setAge(customer.getAge());
          x.setAddresses(customer.getAddresses());
          x.setLastUpdate(customer.getLastUpdate());
          Customer customerUpdated = customerRepository.save(x);
          return ResponseEntity.ok().body(customerUpdated);
            }).orElse(ResponseEntity.notFound().build());

    }

    @PostMapping
    public Customer saveCustomer(@RequestBody Customer customer){

        return customerRepository.save(customer);
    }

    private void removeAddress (Optional<Customer> customer){
        if(customer.isPresent()){
            for(Address address : customer.get().getAddresses()) {
                if (address.getConsumers().size() == 1) {
                    addressRepository.deleteById(address.getId());
                }
            }
        }
    }

    @DeleteMapping(path ={"/{id}"})
    public ResponseEntity<Object> deleteCustomerById(@PathVariable("id") long  id){

        removeAddress(customerRepository.findById(id));
        return customerRepository.findById(id).map(
            x -> {
                customerRepository.deleteById(id);
                return  ResponseEntity.ok().build();
            }).orElse(ResponseEntity.notFound().build());
    }
}
