package com.luist.todo.ui;

import com.luist.todo.model.Todo;
import com.luist.todo.service.TodoRepository;
import com.vaadin.flow.component.KeyNotifier;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;

public class TodoEditor extends VerticalLayout implements KeyNotifier {
    private final TodoRepository repository;
    private Todo todo;

    TextField description = new TextField("Description");
    Checkbox done = new Checkbox("Done");
    DatePicker doneDateTime = new DatePicker("Done Date");


    Button save = new Button("Save", VaadinIcon.CHECK.create());
    Button cancel = new Button("Cancel");
    Button delete = new Button("Delete", VaadinIcon.TRASH.create());
    HorizontalLayout actions = new HorizontalLayout(save, cancel, delete);

    Binder<Todo> binder = new Binder<>(Todo.class);
    private CustomerEditor.ChangeHandler changeHandler;

    public TodoEditor(TodoRepository repository) {
        this.repository = repository;
        add(description, done, doneDateTime);
        // bind using naming convention
        binder.bindInstanceFields(this);

        // Configure and style components
        setSpacing(true);

        save.getElement().getThemeList().add("primary");
        delete.getElement().getThemeList().add("error");

    }
}
