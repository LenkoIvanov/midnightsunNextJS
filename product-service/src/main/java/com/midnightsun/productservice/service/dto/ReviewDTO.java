package com.midnightsun.productservice.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReviewDTO extends AbstractAuditingDTO {

    private Long id;

    private String text;

    @NotNull
    private Long productId;
}
