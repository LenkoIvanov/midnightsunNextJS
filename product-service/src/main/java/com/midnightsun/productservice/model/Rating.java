package com.midnightsun.productservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "rating")
public class Rating extends AbstractAuditingEntity implements Serializable {

    @Id
    @GeneratedValue(generator = "rating_sequence_generator", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "rating_sequence_generator", initialValue = 1000, allocationSize = 1)
    private Long id;

    private Double score;

    @ManyToOne
    private Product product;
}
