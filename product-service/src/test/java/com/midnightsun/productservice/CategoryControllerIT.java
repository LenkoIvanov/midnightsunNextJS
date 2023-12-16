package com.midnightsun.productservice;

import com.midnightsun.productservice.model.Category;
import com.midnightsun.productservice.repository.CategoryRepository;
import com.midnightsun.productservice.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;


import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles("test")
@AutoConfigureMockMvc
@SpringBootTest
public class CategoryControllerIT {

    private static String BASE_API_ENDPOINT = "/api/categories";

    private static String DEFAULT_NAME = "default";
    private static String UPDATED_NAME = "updated";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    private Category category;

    public static Category createCategory() {
        return Category.builder()
                .name(DEFAULT_NAME)
                .build();
    }

    public static Category updateCategory() {
        return Category.builder()
                .name(UPDATED_NAME)
                .build();
    }

    @BeforeEach
    void setUp() {
        productRepository.deleteAll();
        categoryRepository.deleteAll();

        category = createCategory();
    }

    @Test
    @WithMockUser
    void getAll_statusOk() throws Exception {
        final var savedCategory = categoryRepository.saveAndFlush(category);

        mockMvc.perform(get(BASE_API_ENDPOINT))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content.[*].id").value(hasItem(savedCategory.getId().intValue())))
                .andExpect(jsonPath("$.content.[*].name").value(hasItem(DEFAULT_NAME)));
    }

    @Test
    void getAll_statusUnauthorized() throws Exception {
        mockMvc.perform(get(BASE_API_ENDPOINT))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser
    void getOneById_statusOk() throws Exception {
        final var savedCategory = categoryRepository.saveAndFlush(category);

        mockMvc.perform(get(BASE_API_ENDPOINT + "/" + savedCategory.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(savedCategory.getId().intValue()))
                .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }
}
