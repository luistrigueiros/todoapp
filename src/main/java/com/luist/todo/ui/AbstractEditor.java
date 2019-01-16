package com.luist.todo.ui;

import com.vaadin.flow.component.KeyNotifier;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import org.springframework.data.jpa.repository.JpaRepository;

abstract class AbstractEditor<T> extends VerticalLayout implements KeyNotifier {
    public interface ChangeHandler {
        void onChange();
    }

    protected final JpaRepository<T, Long> repository;
    /**
     * The currently edited object
     */
    protected T objectOnEdit;


    protected ChangeHandler changeHandler;

    /* Action buttons */
    // TODO why more code?
    Button save = new Button("Save", VaadinIcon.CHECK.create());
    Button cancel = new Button("Cancel");
    Button delete = new Button("Delete", VaadinIcon.TRASH.create());
    HorizontalLayout actions = new HorizontalLayout(save, cancel, delete);

    protected AbstractEditor(JpaRepository<T, Long> repository) {
        this.repository = repository;
    }


    public void setChangeHandler(ChangeHandler h) {
        // ChangeHandler is notified when either save or delete
        // is clicked
        changeHandler = h;
    }

    void delete() {
        repository.delete(objectOnEdit);
        changeHandler.onChange();
    }

    void save() {
        repository.save(objectOnEdit);
        changeHandler.onChange();
    }
}
