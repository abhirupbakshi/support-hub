package org.example.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

/**
 * <h3>Class Category</h3>
 * This class represents a category of a {@link Complaint}.
 * It's an independent entity and only intended to be used as a category indicator
 * of a complaint.
 */
@Entity
@Table(name = "Categories")
@SuperBuilder
@NoArgsConstructor(force = true)
@Getter
@Setter
@Accessors(chain = true)
public class Category {

    /**
     * <h4>Optional</h4>
     * Name of the category.
     * Also used as primary key in the database.
     * Cannot be set after the object is created.
     */
    @Id
    @Column(name = "category_name")
    @Setter(value = AccessLevel.NONE)
    @NonNull
    private String name;

    /**
     * <h4>Optional</h4>
     */
    @Column(name = "description")
    private String description;
}
