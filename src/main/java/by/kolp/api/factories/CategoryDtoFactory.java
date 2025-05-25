package by.kolp.api.factories;

import by.kolp.api.model.dto.CategoryDTO;
import by.kolp.api.model.entity.Category;

public class CategoryDtoFactory {

    public CategoryDTO makeCategoryDto(Category category) {

        return CategoryDTO.builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }
}
