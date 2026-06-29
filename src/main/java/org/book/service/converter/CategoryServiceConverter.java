package org.book.service.converter;

import org.book.domain.category.model.Category;
import org.book.service.dto.CategoryDTO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 分类服务转换器
 * <p>负责分类领域对象与 DTO 之间的转换</p>
 */
@Component
public class CategoryServiceConverter {

    /**
     * 将领域对象转换为 DTO
     *
     * @param category 分类领域对象
     * @return 分类 DTO
     */
    public CategoryDTO toDTO(Category category) {
        if (category == null) return null;
        CategoryDTO dto = new CategoryDTO();
        dto.setId(category.getId());
        dto.setName(category.getName());
        dto.setDescription(category.getDescription());
        dto.setSortOrder(category.getSortOrder());
        dto.setCreatedAt(category.getCreatedAt());
        dto.setUpdatedAt(category.getUpdatedAt());
        return dto;
    }

    /**
     * 将领域对象列表转换为 DTO 列表
     *
     * @param categories 分类领域对象列表
     * @return 分类 DTO 列表
     */
    public List<CategoryDTO> toDTOList(List<Category> categories) {
        return categories.stream().map(this::toDTO).collect(Collectors.toList());
    }
}
