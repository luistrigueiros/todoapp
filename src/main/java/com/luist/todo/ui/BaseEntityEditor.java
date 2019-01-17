package com.luist.todo.ui;

import com.luist.todo.model.PersistableEntity;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyNotifier;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.binder.Binder;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.annotation.PostConstruct;
import java.util.Optional;

/**
 * The base reuseable code related to editing a persistent entity
 *
 * @param <T> the entity type to be edited
 */
abstract class BaseEntityEditor<T extends PersistableEntity> extends VerticalLayout implements KeyNotifier {
    public interface ChangeHandler {
        void onChange();
    }

    private final JpaRepository<T, Long> repository;
    /**
     * The currently edited object
     */
    protected T objectOnEdit;


    private ChangeHandler changeHandler;

    private final Binder<T> binder;

    private Button save = new Button("Save", VaadinIcon.CHECK.create());
    private Button cancel = new Button("Cancel");
    private Button delete = new Button("Delete", VaadinIcon.TRASH.create());
    HorizontalLayout actions = new HorizontalLayout(save, cancel, delete);

    protected BaseEntityEditor(JpaRepository<T, Long> repository, Binder<T> binder) {
        this.repository = repository;
        this.binder = binder;

        // Configure and style components
        setSpacing(true);

        save.getElement().getThemeList().add("primary");
        delete.getElement().getThemeList().add("error");

        addKeyPressListener(Key.ENTER, e -> save());

        // wire action buttons to save, delete and reset
        save.addClickListener(e -> save());
        delete.addClickListener(e -> delete());
        cancel.addClickListener(e -> startEditing(null));

        setVisible(false);
    }

    final void startEditing(T objectToEdit) {
        if (objectToEdit == null) {
            setVisible(false);
            return;
        }
        final boolean persisted = objectToEdit.getId() != null;
        if (persisted) {
            // Find fresh entity for editing
            Optional<T> found = repository.findById(objectToEdit.getId());
            if (found.isPresent()) {
                objectOnEdit = found.get();
            } else {
                Notification.show("This an error should not happen, persistent entity not found in db!");
            }
        } else {
            objectOnEdit = objectToEdit;
        }
        cancel.setVisible(persisted);
        binder.setBean(objectOnEdit);
        setVisible(true);
        onEditStarted();
    }

    void setChangeHandler(ChangeHandler h) {
        // ChangeHandler is notified when either save or delete
        // is clicked
        changeHandler = h;
    }

    protected void delete() {
        repository.delete(objectOnEdit);
        changeHandler.onChange();
    }

    protected void save() {
        repository.save(objectOnEdit);
        changeHandler.onChange();
    }

    /**
     * Init this editor binder
     *
     * @param binder the binder been configured
     */
    protected abstract void initBinder(Binder<T> binder);

    /**
     * Actions to perform on entering edit mode
     */
    protected abstract void onEditStarted();

    @PostConstruct
    public final void postInit() {
        initBinder(binder);
    }
}
