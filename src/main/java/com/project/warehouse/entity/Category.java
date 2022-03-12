package com.project.warehouse.entity;

import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "active", nullable = false)
    private Boolean active;

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne()
    @JoinColumn(name = "parent_category_id")
    @ToString.Exclude
    private Category parentCategory;

    @Override
    public String toString() {
        return "{" +
                "id:" + id +
                ", active:" + active +
                ", name:\"" + name + '\"' +
                ", parentCategory:" + parentCategory +
                '}';
    }
}