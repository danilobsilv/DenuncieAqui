package com.api.denuncieAqui.features.categories;

import com.api.denuncieAqui.features.categories.types.CategoryResponseType;
import com.api.denuncieAqui.features.categories.types.CreateCategoryType;
import com.api.denuncieAqui.features.categories.types.UpdateCategoryType;
import com.api.denuncieAqui.features.categories.validations.CategoryValidator;
import com.api.denuncieAqui.infra.exceptionsHandler.resourceConflictException.ResourceConflictException;
import com.api.denuncieAqui.infra.exceptionsHandler.resourceNotFoundException.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class CategoriesService {

    @Autowired
    CategoriesRepository categoriesRepository;

    @Autowired
    List<CategoryValidator> validators;

    public Categories createCategory(CreateCategoryType data){
        validators.forEach(validator -> validator.validate(data));

        var category = new Categories(data);
        return categoriesRepository.save(category);
    }

    public Page<CategoryResponseType> getCategories(Pageable pageable){
        return categoriesRepository.findAll(pageable).map(CategoryResponseType::new);
    }

    public List<Categories> listActiveCategories(){
        List<Categories> response = categoriesRepository.findByisCategoryActiveTrue();
        response.forEach(resp -> System.out.println(resp.toString()));
        return response;
    }

    @Transactional
    public Categories updateCategory(UUID categoryId, UpdateCategoryType data){
        Categories category = categoriesRepository.findById(categoryId)
            .orElseThrow(() -> new ResourceNotFoundException("Categoria não encontrada com o ID: " + categoryId));

        boolean existsByName = categoriesRepository.existsByCategoryNameAndCategoryId(data.categoryName(), categoryId);
        if (existsByName){
            throw new ResourceConflictException("Já existe uma categoria com esse nome. Tente novamente!");
        }
        category.updateCategoryInformation(data);

        return category;
    }

    @Transactional
    public void deactivateOrReactivateCategory(UUID categoryId) {
        Categories category = categoriesRepository.findById(categoryId)
            .orElseThrow(() -> new ResourceNotFoundException("Categoria não encontrada com o ID: " + categoryId));

        boolean newStatus = !category.isCategoryActive();
        category.setCategoryActive(newStatus);

    }
}
