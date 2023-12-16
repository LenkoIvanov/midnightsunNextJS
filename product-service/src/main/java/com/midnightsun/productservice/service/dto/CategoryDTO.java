package com.midnightsun.productservice.service.dto;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDTO extends AbstractAuditingDTO {

    private Long id;

    private String name;
}

