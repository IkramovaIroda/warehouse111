package com.project.warehouse.entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "active", nullable = false)
    private Boolean active = true;

    @Type(type = "org.hibernate.type.PostgresUUIDType")
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "code", unique = true, updatable = false)
    private Long code;

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne()
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @ManyToOne()
    @JoinColumn(name = "measurement_id", nullable = false)
    private Measurement measurement;
//    @ManyToOne()
//    @JoinColumn(name = "photo_id", nullable = false)
//    private Attachment attachment;
}