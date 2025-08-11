package by.kolp.myappdataapi.factories;

import by.kolp.myappdataapi.dto.CategoryDTO;
import by.kolp.myappcore.model.entity.Category;

public class CategoryDtoFactory {

    public CategoryDTO makeCategoryDto(Category category) {

        return CategoryDTO.builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }
}
