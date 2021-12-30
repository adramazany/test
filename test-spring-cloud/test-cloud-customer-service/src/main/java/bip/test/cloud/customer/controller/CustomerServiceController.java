package bip.test.cloud.customer.controller;


import bip.test.cloud.customer.model.Customer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by ramezani on 5/27/2020.
 */
@RestController
public class CustomerServiceController {
    private static Map<String, Customer> customerRepo = new HashMap<>();
    static {
        Customer honey = new Customer();
        honey.setId("1");
        honey.setName("ali");
        customerRepo.put(honey.getId(), honey);

        Customer almond = new Customer();
        almond.setId("2");
        almond.setName("mohammad");
        customerRepo.put(almond.getId(), almond);
    }

    @RequestMapping(value = "/customers/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> delete(@PathVariable("id") String id) {
        customerRepo.remove(id);
        return new ResponseEntity<>("Customer is deleted successsfully", HttpStatus.OK);
    }

    @RequestMapping(value = "/customers/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Object> updateCustomer(@PathVariable("id") String id, @RequestBody Customer customer) {
        customerRepo.remove(id);
        customer.setId(id);
        customerRepo.put(id, customer);
        return new ResponseEntity<>("Customer is updated successsfully", HttpStatus.OK);
    }

    @RequestMapping(value = "/customers", method = RequestMethod.POST)
    public ResponseEntity<Object> createCustomer(@RequestBody Customer customer) {
        customerRepo.put(customer.getId(), customer);
        return new ResponseEntity<>("Customer is created successfully", HttpStatus.CREATED);
    }

    @RequestMapping(value = "/customers")
    public ResponseEntity<Object> getCustomer() {
        return new ResponseEntity<>(customerRepo.values(), HttpStatus.OK);
    }
}