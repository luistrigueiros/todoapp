package com.luist.todo.ui;

import com.luist.todo.model.Todo;
import com.luist.todo.service.TodoRepository;
import com.vaadin.flow.component.KeyNotifier;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;

@UIScope
@SpringComponent
public class TodoEditor extends BaseEntityEditor<Todo> {
    TextField description = new TextField("Description");
    Checkbox done = new Checkbox("Done");
    DatePicker doneDateTime = new DatePicker("Done Date");

    public TodoEditor(TodoRepository repository) {
        super(repository, new Binder<>(Todo.class));
        add(description, done, doneDateTime);
    }

    @Override
    protected void onEditStarted() {

    }
}