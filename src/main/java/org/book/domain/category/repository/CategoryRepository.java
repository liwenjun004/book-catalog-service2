package org.book.domain.category.repository;

import org.book.domain.category.model.Category;

import java.util.List;

/**
 * 分类仓储接口
 * <p>定义分类领域的数据访问操作，由基础设施层实现</p>
 */
public interface CategoryRepository {

    /**
     * 根据ID查询分类
     *
     * @param id 分类ID
     * @return 分类领域对象，不存在时返回null
     */
    Category findById(Long id);

    /**
     * 查询所有分类（按排序号升序）
     *
     * @return 分类列表
     */
    List<Category> findAll();

    /**
     * 保存新分类
     *
     * @param category 分类领域对象
     */
    void save(Category category);

    /**
     * 更新分类信息
     *
     * @param category 分类领域对象
     */
    void update(Category category);

    /**
     * 根据ID删除分类
     *
     * @param id 分类ID
     */
    void deleteById(Long id);

    /**
     * 检查分类名称是否已存在
     *
     * @param name 分类名称
     * @return true表示已存在
     */
    boolean existsByName(String name);

    /**
     * 检查分类名称是否已被其他分类使用（排除指定ID）
     *
     * @param name 分类名称
     * @param id   当前分类ID
     * @return true表示已被使用
     */
    boolean existsByNameAndIdNot(String name, Long id);
}
