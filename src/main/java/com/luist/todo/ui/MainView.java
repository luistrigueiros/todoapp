package com.luist.todo.ui;

import com.luist.todo.service.CustomerRepository;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.PWA;

@Route
@PWA(name = "Sample TodoApp based on Vaadin Flow and Spring", shortName = "TodoApp")
public class MainView extends VerticalLayout {

    public MainView(CustomerRepository repo, CustomerEditor editor) {
        add(new CustomerPanel(repo, editor));
    }
}
