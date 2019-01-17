package com.luist.todo.ui;

import com.luist.todo.model.Customer;
import com.luist.todo.service.CustomerRepository;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;

@UIScope
@SpringComponent
public class CustomerEditor extends BaseEntityEditor<Customer> {
    /* Fields to edit properties in Customer entity */
    TextField firstName = new TextField("First name");
    TextField lastName = new TextField("Last name");

    @Autowired
    public CustomerEditor(CustomerRepository repository) {
        super(repository, new Binder<>(Customer.class));

        add(firstName, lastName, actions);
    }

    @Override
    protected void initBinder(Binder<Customer> binder) {
        binder.forField(firstName).bind(Customer::getFirstName, Customer::setFirstName);
        binder.forField(lastName).bind(Customer::getLastName, Customer::setLastName);
    }

    @Override
    protected void onEditStarted() {
        // Focus first name initially
        firstName.focus();
    }
}
