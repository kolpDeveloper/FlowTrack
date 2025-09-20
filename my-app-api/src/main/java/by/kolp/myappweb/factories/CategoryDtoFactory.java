package by.kolp.myappweb.factories;

import by.kolp.myappweb.dto.CategoryDTO;
import by.kolp.myappcore.model.entity.Category;

public class CategoryDtoFactory {

    public CategoryDTO makeCategoryDto(Category category) {

        return CategoryDTO.builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }
}
