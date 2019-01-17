package com.luist.todo.ui;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.PWA;

@Route
@PWA(name = "Sample TodoApp based on Vaadin Flow and Spring", shortName = "TodoApp")
public class MainView extends VerticalLayout {

    public MainView(TodoPanel panel) {
        add(panel);
    }
}
