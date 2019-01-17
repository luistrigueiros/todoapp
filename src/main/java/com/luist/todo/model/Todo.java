package com.luist.todo.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class Todo implements PersistableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    private String description;

    @NotNull
    private boolean done;

    @NotNull
    private LocalDateTime doneDateTime = LocalDateTime.now();

//    @NotNull
//    private LocalDateTime lastModifyDateTime;

    public void toggleDone() {
        done = !done;
    }
}
