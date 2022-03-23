package com.project.warehouse.entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "output")
public class Output {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "code", unique = true, nullable = false)
    private UUID code=UUID.randomUUID();

    @ManyToOne()
    @JoinColumn(name = "currency_id", nullable = false)
    @ToString.Exclude
    private Currency currency;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "facture_number", nullable = false)
    private Integer factureNumber;

//    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @ManyToOne()
    @JoinColumn(name = "warehouse_id", nullable = false)
    @ToString.Exclude
    private Warehouse warehouse;

//    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @ManyToOne()
    @JoinColumn(name = "client_id", nullable = false)
    @ToString.Exclude
    private Client client;

    @Column(name = "active", nullable = false)
    private boolean active=true;

}