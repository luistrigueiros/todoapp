package com.luist.todo.ui;

import com.luist.todo.model.PersistableEntity;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyNotifier;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.binder.Binder;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * The base reuseable code related to editing a persistent entity
 *
 * @param <T> the entity type to be edited
 */
abstract class BaseEntityEditor<T extends PersistableEntity> extends VerticalLayout implements KeyNotifier {
    public interface ChangeHandler {
        void onChange();
    }

    protected final JpaRepository<T, Long> repository;
    /**
     * The currently edited object
     */
    protected T objectOnEdit;


    protected ChangeHandler changeHandler;

    protected final Binder<T> binder;

    private Button save = new Button("Save", VaadinIcon.CHECK.create());
    private Button cancel = new Button("Cancel");
    private Button delete = new Button("Delete", VaadinIcon.TRASH.create());
    protected HorizontalLayout actions = new HorizontalLayout(save, cancel, delete);

    protected BaseEntityEditor(JpaRepository<T, Long> repository, Binder<T> binder) {
        this.repository = repository;
        this.binder = binder;
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
        cancel.addClickListener(e -> startEditing(objectOnEdit));

        setVisible(false);
    }

    public final void startEditing(T objectToEdit) {
        if (isNotEditable(objectToEdit)) return;
        onEditStarted();
    }


    public void setChangeHandler(ChangeHandler h) {
        // ChangeHandler is notified when either save or delete
        // is clicked
        changeHandler = h;
    }

    private void delete() {
        repository.delete(objectOnEdit);
        changeHandler.onChange();
    }

    private void save() {
        repository.save(objectOnEdit);
        changeHandler.onChange();
    }

    /**
     * Returns true if can not edit object
     *
     * @param objectToEdit
     * @return
     */
    private boolean isNotEditable(T objectToEdit) {
        if (objectToEdit == null) {
            setVisible(false);
            return true;
        }
        final boolean persisted = objectToEdit.getId() != null;
        if (persisted) {
            // Find fresh entity for editing
            repository.findById(objectToEdit.getId())
                    .ifPresent(found -> objectOnEdit = found);
        } else {
            objectOnEdit = objectToEdit;
        }
        cancel.setVisible(persisted);
        // Bind customer properties to similarly named fields
        // Could also use annotation or "manual binding" or programmatically
        // moving values from fields to entities before saving
        binder.setBean(objectOnEdit);
        setVisible(true);
        return false;
    }

    /**
     * Actions to perform on entering edit mode
     */
    protected abstract void onEditStarted();
}
