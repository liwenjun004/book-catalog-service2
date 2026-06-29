package org.book.infra.converter;

import org.book.domain.category.model.Category;
import org.book.infra.dataobject.CategoryDO;
import org.springframework.stereotype.Component;

/**
 * 分类转换器
 * <p>负责分类领域对象与数据对象之间的转换</p>
 */
@Component
public class CategoryConverter {

    /**
     * 将领域对象转换为数据对象
     *
     * @param category 分类领域对象
     * @return 分类数据对象
     */
    public CategoryDO toDataObject(Category category) {
        CategoryDO doObj = new CategoryDO();
        doObj.setId(category.getId());
        doObj.setName(category.getName());
        doObj.setDescription(category.getDescription());
        doObj.setSortOrder(category.getSortOrder());
        doObj.setCreatedAt(category.getCreatedAt());
        doObj.setUpdatedAt(category.getUpdatedAt());
        return doObj;
    }

    /**
     * 将数据对象转换为领域对象
     *
     * @param doObj 分类数据对象
     * @return 分类领域对象
     */
    public Category toDomain(CategoryDO doObj) {
        if (doObj == null) {
            return null;
        }
        return Category.reconstruct(
                doObj.getId(),
                doObj.getName(),
                doObj.getDescription(),
                doObj.getSortOrder(),
                doObj.getCreatedAt(),
                doObj.getUpdatedAt()
        );
    }
}
