package com.api.denuncieAqui.features.categories;

import com.api.denuncieAqui.features.categories.types.CategoryResponseType;
import com.api.denuncieAqui.features.categories.types.CreateCategoryType;
import com.api.denuncieAqui.features.categories.types.UpdateCategoryType;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/categories")
public class CategoriesController {

    @Autowired
    CategoriesService categoriesService;

    @PostMapping("/create")
    public ResponseEntity<CategoryResponseType> createCategory(@RequestBody @Valid CreateCategoryType data, UriComponentsBuilder uriComponentsBuilder){
        Categories category = categoriesService.createCategory(data);

        var uri = uriComponentsBuilder.path("/category/{category_id}").buildAndExpand(category.getCategoryId()).toUri();
        System.out.println("server uri response: " + uri);

        return ResponseEntity.created(uri).body(new CategoryResponseType(category));
    }

    @GetMapping("/admin")
    public ResponseEntity<Page<CategoryResponseType>> getCategories(@PageableDefault(size=10, sort={"categoryName"}) Pageable pageable){
        var page = categoriesService.getCategories(pageable);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/")
    public ResponseEntity<List<CategoryResponseType>> listActiveCategories(){
        List<Categories> activeCategories = categoriesService.listActiveCategories();

        List<CategoryResponseType> responseList = activeCategories.stream()
                .map(CategoryResponseType::new)
                .toList();

        return ResponseEntity.ok(responseList);
    }

    @PutMapping("/categoryId/{categoryId}")
    public ResponseEntity<CategoryResponseType> updateCategory(@PathVariable UUID categoryId, @RequestBody @Valid UpdateCategoryType data){
        Categories category = categoriesService.updateCategory(categoryId, data);
        return ResponseEntity.ok(new CategoryResponseType(category));
    }

        @PatchMapping("/categoryId/{categoryId}")
    public ResponseEntity<Void> deactivateOrReactivateCategory(@PathVariable UUID categoryId) {
        categoriesService.deactivateOrReactivateCategory(categoryId);
        return ResponseEntity.noContent().build();
    }
}
