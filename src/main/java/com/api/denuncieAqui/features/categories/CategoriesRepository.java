package com.api.denuncieAqui.features.categories;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CategoriesRepository extends JpaRepository<Categories, UUID> {
    boolean existsByCategoryName(String categoryName);
    boolean existsByCategoryNameAndCategoryId(String categoryName, UUID categoryId);
    List<Categories> findByisCategoryActiveTrue();
}
