package com.api.denuncieAqui.categories;

import com.api.denuncieAqui.features.categories.Categories;
import com.api.denuncieAqui.features.categories.CategoriesRepository;
import com.api.denuncieAqui.features.categories.CategoriesService;
import com.api.denuncieAqui.features.categories.types.CategoryResponseType;
import com.api.denuncieAqui.features.categories.types.CreateCategoryType;
import com.api.denuncieAqui.features.categories.validations.ValidateEmptyOrNullName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class CategoriesServiceTest {

    @Autowired
    CategoriesService service;

    @Autowired
    CategoriesRepository repository;


    @Test
    void shouldCreateCategorySuccessfully() {
        CreateCategoryType dto = new CreateCategoryType("H2 Teste", "Descrição teste");

        Categories category = service.createCategory(dto);

        assertThat(category.getCategoryId()).isNotNull();
        assertThat(category.getCategoryName()).isEqualTo("H2 Teste");
        assertThat(repository.findById(category.getCategoryId())).isPresent();
    }

    @Test
    void shouldFailWhenNameIsInvalid() {
        CreateCategoryType dto = new CreateCategoryType("", "Descrição");

        try {
            service.createCategory(dto);
        } catch (Exception e) {
            assertThat(e).isInstanceOf(RuntimeException.class);
        }
    }

    @Test
    void shouldReturnPageOfCategories() {

        Categories category = new Categories(new CreateCategoryType("Categoria de teste", "Descrição da categoria de teste"));

        Page<Categories> page = new PageImpl<>(List.of(category));
        when(repository.findAll(Mockito.any(Pageable.class))).thenReturn(page);

        Page<CategoryResponseType> result = service.getCategories(PageRequest.of(0, 10, Sort.by("categoryName")));

        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().get(0).name()).isEqualTo("Categoria de teste");
    }
}

