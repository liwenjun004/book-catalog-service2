package org.book.service;

import org.book.service.dto.CategoryCreateRequest;
import org.book.service.dto.CategoryDTO;
import org.book.service.dto.CategoryUpdateRequest;

import java.util.List;

/**
 * 分类服务接口
 * <p>提供图书分类的创建、查询、修改、删除等业务操作</p>
 */
public interface CategoryService {

    /**
     * 创建新分类
     *
     * @param request 创建请求参数
     * @return 创建成功的分类信息
     * @throws org.book.common.BusinessException 分类名称已存在时抛出
     */
    CategoryDTO create(CategoryCreateRequest request);

    /**
     * 根据ID查询分类详情
     *
     * @param id 分类ID
     * @return 分类信息
     * @throws org.book.common.BusinessException 分类不存在时抛出
     */
    CategoryDTO getById(Long id);

    /**
     * 查询所有分类列表
     *
     * @return 分类列表
     */
    List<CategoryDTO> listAll();

    /**
     * 更新分类信息
     *
     * @param id      分类ID
     * @param request 更新请求参数
     * @return 更新后的分类信息
     * @throws org.book.common.BusinessException 分类不存在或名称重复时抛出
     */
    CategoryDTO update(Long id, CategoryUpdateRequest request);

    /**
     * 删除分类
     *
     * @param id 分类ID
     * @throws org.book.common.BusinessException 分类不存在或分类下存在图书时抛出
     */
    void delete(Long id);
}
