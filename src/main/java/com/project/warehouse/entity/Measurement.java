package com.project.warehouse.entity;

import lombok.*;
import org.springframework.boot.context.properties.bind.DefaultValue;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "measurement")
public class Measurement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "active", nullable = false)
    private Boolean active;

    @Column(name = "name", nullable = false)
    private String name;

    @Override
    public String toString() {
        return "{" +
                "id:" + id +
                ", active:" + active +
                ", name:\"" + name + '\"' +
                '}';
    }
}