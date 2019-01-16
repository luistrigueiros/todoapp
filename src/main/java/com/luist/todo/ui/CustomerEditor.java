package com.luist.todo.ui;

import com.luist.todo.model.Customer;
import com.luist.todo.service.CustomerRepository;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * A simple example to introduce building forms. As your real application is probably much
 * more complicated than this example, you could re-use this form in multiple places. This
 * example component is only used in MainView.
 * <p>
 * In a real world application you'll most likely using a common super class for all your
 * forms - less code, better UX.
 */
@SpringComponent
@UIScope
public class CustomerEditor extends AbstractEditor<Customer> {
	/* Fields to edit properties in Customer entity */
	TextField firstName = new TextField("First name");
	TextField lastName = new TextField("Last name");

	Binder<Customer> binder = new Binder<>(Customer.class);

	@Autowired
	public CustomerEditor(CustomerRepository repository) {
		super(repository);

		add(firstName, lastName, actions);

		// bind using naming convention
		binder.bindInstanceFields(this);

		// Configure and style components
		setSpacing(true);

		save.getElement().getThemeList().add("primary");
		delete.getElement().getThemeList().add("error");

		addKeyPressListener(Key.ENTER, e -> save());

		// wire action buttons to save, delete and reset
		save.addClickListener(e -> save());
		delete.addClickListener(e -> delete());
		cancel.addClickListener(e -> editCustomer(objectOnEdit));
		setVisible(false);
	}


	public final void editCustomer(Customer c) {
		if (isNotEditable(c)) return;
		// Focus first name initially
		firstName.focus();
	}

	/**
	 * Returns true if can not edit object
	 * @param c
	 * @return
	 */
	protected boolean isNotEditable(Customer c) {
		if (c == null) {
			setVisible(false);
			return true;
		}
		final boolean persisted = c.getId() != null;
		if (persisted) {
			// Find fresh entity for editing
			objectOnEdit = repository.findById(c.getId()).get();
		}
		else {
			objectOnEdit = c;
		}
		cancel.setVisible(persisted);

		// Bind customer properties to similarly named fields
		// Could also use annotation or "manual binding" or programmatically
		// moving values from fields to entities before saving
		binder.setBean(objectOnEdit);

		setVisible(true);
		return false;
	}
}
