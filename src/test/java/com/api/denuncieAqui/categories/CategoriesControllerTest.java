package com.api.denuncieAqui.categories;

import com.api.denuncieAqui.features.categories.Categories;
import com.api.denuncieAqui.features.categories.CategoriesRepository;
import com.api.denuncieAqui.features.categories.types.CreateCategoryType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class CategoriesControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    CategoriesRepository categoriesRepository;

    @BeforeEach
    void setup() {
        categoriesRepository.deleteAll();
        categoriesRepository.save(new Categories(new CreateCategoryType("Categoria A", "Descrição da Categoria A")));
        categoriesRepository.save(new Categories(new CreateCategoryType("Categoria B", "Descrição da Categoria B")));
    }

    @Test
    void shouldCreateCategoryAndReturn201() throws Exception {
        CreateCategoryType dto = new CreateCategoryType("Controller H2", "Descrição teste");

        mockMvc.perform(post("/api/v1/categories/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Controller H2"))
                .andExpect(jsonPath("$.description").value("Descrição teste"));
    }


    @Test
    void shouldReturnPagedCategories() throws Exception {
        mockMvc.perform(get("/api/v1/categories/admin")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(2)))
                .andExpect(jsonPath("$.content[0].categoryName", is("Categoria A")))
                .andExpect(jsonPath("$.content[1].categoryName", is("Categoria B")));
    }
}
