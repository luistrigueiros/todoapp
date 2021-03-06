package com.luist.todo.config;

import com.luist.todo.model.Customer;
import com.luist.todo.model.Todo;
import com.luist.todo.service.CustomerRepository;
import com.luist.todo.service.TodoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InitialDataLoaderConfig {
    private static final Logger log = LoggerFactory.getLogger(InitialDataLoaderConfig.class);

    @Bean
    public CommandLineRunner loadData(CustomerRepository repository) {
        return args -> {
            // save a couple of customers
            repository.save(new Customer("Jack", "Bauer"));
            repository.save(new Customer("Chloe", "O'Brian"));
            repository.save(new Customer("Kim", "Bauer"));
            repository.save(new Customer("David", "Palmer"));
            repository.save(new Customer("Michelle", "Dessler"));

            // fetch all customers
            log.info("Customers found with findAll():");
            log.info("-------------------------------");
            for (Customer customer : repository.findAll()) {
                log.info(customer.toString());
            }
            log.info("");

            // fetch an individual customer by ID
            Customer customer = repository.findById(1L).get();
            log.info("Customer found with findOne(1L):");
            log.info("--------------------------------");
            log.info(customer.toString());
            log.info("");

            // fetch customers by last name
            log.info("Customer found with findByLastNameStartsWithIgnoreCase('Bauer'):");
            log.info("--------------------------------------------");
            for (Customer bauer : repository
                    .findByLastNameStartsWithIgnoreCase("Bauer")) {
                log.info(bauer.toString());
            }
            log.info("");
        };
    }

    @Bean
    public CommandLineRunner loadTodo(TodoRepository repository) {
        return args -> {
            Todo todo1 = new Todo();
            todo1.setDescription("Example task to do");
            todo1.updateLastModify();
            repository.save(todo1);
            log.info("Todo found with findAll():");
            log.info("-------------------------------");
            repository.findAll().forEach( todo -> log.info(todo.toString()));
        };
    }

}
