package com.luist.todo.ui;

import com.luist.todo.model.Todo;
import com.luist.todo.service.TodoRepository;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;

@UIScope
@SpringComponent
public class TodoEditor extends BaseEntityEditor<Todo> {
    private TextArea description = new TextArea("Description");
    private Checkbox done = new Checkbox("Done");
    private TextField lastModify = new TextField("Last modify");


    public TodoEditor(TodoRepository repository) {
        super(repository, new Binder<>(Todo.class));
        add(description, done, lastModify, actions);
        lastModify.setPlaceholder("Last modify");
        lastModify.setEnabled(false);
    }

    @Override
    protected void initBinder(Binder<Todo> binder) {
        binder.forField(description).bind(Todo::getDescription, Todo::setDescription);
        binder.forField(done).bind(Todo::isDone, Todo::setDone);
        binder.forField(lastModify).bind(Todo::getLastModify, Todo::setLastModify);
    }

    @Override
    protected void onEditStarted() {
        description.focus();
    }

    @Override
    protected void save() {
        super.objectOnEdit.updateLastModify();
        super.save();
    }
}
