package com.project.warehouse.entity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "active", nullable = false)
    private Boolean active = false;

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "code", nullable = false)
    private Long code;

    @Column(name = "name", nullable = false)
    private String name;

//    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @ManyToOne()
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

//    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @ManyToOne()
    @JoinColumn(name = "measurement_id", nullable = false)
    private Measurement measurement;

    @Override
    public String toString() {
        return "{" +
                "id:" + id +
                ", active:" + active +
                ", code:" + code +
                ", name: \"" + name + '\"' +
                ", category:" + category +
                ", measurement:" + measurement +
                '}';
    }
}