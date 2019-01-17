package com.luist.todo.ui;

import com.google.common.base.Preconditions;
import com.luist.todo.model.Todo;
import com.luist.todo.service.TodoRepository;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.util.StringUtils;

@UIScope
@SpringComponent
public class TodoPanel extends VerticalLayout {
    private final TodoRepository repository;
    private final TodoEditor editor;
    private final Grid<Todo> grid = new Grid<>(Todo.class);
    private final Button addNewBtn;

    public TodoPanel(TodoRepository repository, TodoEditor editor) {
        this.repository = repository;
        this.editor = editor;
        this.addNewBtn = new Button("New Todo", VaadinIcon.PLUS.create());

        // build layout
        HorizontalLayout actions = new HorizontalLayout(addNewBtn);
        add(actions, grid, editor);

        grid.setHeight("300px");
        grid.setColumns("id", "done", "description");
        grid.getColumnByKey("id").setWidth("50px").setFlexGrow(0);

        // Connect selected Todo to editor or hide if none is selected
        grid.asSingleSelect().addValueChangeListener(e -> editor.startEditing(e.getValue()));

        // Instantiate and edit new Customer the new button is clicked
        addNewBtn.addClickListener(e -> editor.startEditing(new Todo()));

        // Listen changes made by the editor, refresh data from backend
        editor.setChangeHandler(() -> {
            editor.setVisible(false);
            //listCustomers(filter.getValue());
        });
        listTodos(null);
    }

    private void listTodos(String filterText) {
        if (StringUtils.isEmpty(filterText)) {
            grid.setItems(repository.findAll());
        } else {
            Preconditions.checkState(false, "Yet to implement");
            //grid.setItems(repo.findByLastNameStartsWithIgnoreCase(filterText));
        }
    }

}
