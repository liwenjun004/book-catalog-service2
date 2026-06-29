package org.book.domain.category.model;

import lombok.Getter;
import org.book.common.BusinessException;

import java.time.LocalDateTime;

/**
 * 分类领域实体（充血模型）
 * <p>封装分类的核心业务逻辑，包括名称校验、信息更新等操作</p>
 * <p>使用私有构造器 + 静态工厂方法，确保对象创建的合法性</p>
 */
@Getter
public class Category {

    /** 分类ID（主键） */
    private Long id;

    /** 分类名称（必填，最长100字符） */
    private String name;

    /** 分类描述（可选，最长500字符） */
    private String description;

    /** 排序号（默认为0，数值越小越靠前） */
    private Integer sortOrder;

    /** 创建时间 */
    private LocalDateTime createdAt;

    /** 最后更新时间 */
    private LocalDateTime updatedAt;

    /** 私有构造器，通过静态工厂方法创建实例 */
    private Category() {}

    /**
     * 工厂方法：创建新分类
     */
    public static Category create(String name, String description, Integer sortOrder) {
        Category category = new Category();
        category.validateName(name);
        category.name = name;
        category.description = description;
        category.sortOrder = sortOrder != null ? sortOrder : 0;
        category.createdAt = LocalDateTime.now();
        category.updatedAt = LocalDateTime.now();
        return category;
    }

    /**
     * 工厂方法：从持久化重建
     */
    public static Category reconstruct(Long id, String name, String description,
                                       Integer sortOrder, LocalDateTime createdAt, LocalDateTime updatedAt) {
        Category category = new Category();
        category.id = id;
        category.name = name;
        category.description = description;
        category.sortOrder = sortOrder;
        category.createdAt = createdAt;
        category.updatedAt = updatedAt;
        return category;
    }

    /**
     * 更新分类信息
     *
     * @param name        新的分类名称
     * @param description 新的分类描述
     * @param sortOrder   新的排序号
     * @throws BusinessException 名称校验失败时抛出
     */
    public void update(String name, String description, Integer sortOrder) {
        validateName(name);
        this.name = name;
        this.description = description;
        this.sortOrder = sortOrder != null ? sortOrder : this.sortOrder;
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * 校验分类名称合法性
     *
     * @param name 待校验的分类名称
     * @throws BusinessException 名称为空或超长时抛出
     */
    private void validateName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new BusinessException("分类名称不能为空");
        }
        if (name.length() > 100) {
            throw new BusinessException("分类名称不能超过100个字符");
        }
    }

    /**
     * 判断当前分类名称是否与指定名称相同
     *
     * @param name 待比较的名称
     * @return true表示名称相同
     */
    public boolean hasName(String name) {
        return this.name != null && this.name.equals(name);
    }
}
