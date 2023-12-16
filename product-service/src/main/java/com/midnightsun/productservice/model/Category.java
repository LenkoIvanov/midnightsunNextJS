package com.midnightsun.productservice.model;

import javax.persistence.*;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "category")
public class Category extends AbstractAuditingEntity {

    @Id
    @GeneratedValue(generator = "category_sequence_generator", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "category_sequence_generator", initialValue = 1000, allocationSize = 1)
    private Long id;

    private String name;
}
