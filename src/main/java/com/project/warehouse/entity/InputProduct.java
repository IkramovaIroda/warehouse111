package com.project.warehouse.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "input_product")
public class InputProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

//    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @ManyToOne()
    @JoinColumn(name = "product_id", nullable = false)
    @ToString.Exclude
    private Product product;

    @Column(name = "amount", nullable = false, precision = 131089)
    private Double amount;

    @Column(name = "price", nullable = false, precision = 131089)
    private Double price;


    @Column(name = "expire_date", nullable = false)
    private LocalDate expireDate;

//    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @ManyToOne()
    @JoinColumn(name = "input_id", nullable = false)
    @ToString.Exclude
    private Input input;

}