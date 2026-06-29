package org.book.controller;

import org.book.common.Result;
import org.book.service.CategoryService;
import org.book.service.dto.CategoryCreateRequest;
import org.book.service.dto.CategoryDTO;
import org.book.service.dto.CategoryUpdateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 分类管理控制器
 * <p>提供图书分类的RESTful API接口，包括分类的创建、查询、修改和删除操作</p>
 * <p>基础路径：/api/categories</p>
 */
@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    /** 分类服务 */
    @Autowired
    private CategoryService categoryService;

    /**
     * 创建新分类
     *
     * @param request 创建请求参数（包含分类名称、描述、排序号）
     * @return 创建成功的分类信息
     */
    @PostMapping("/create")
    public Result<CategoryDTO> create(@RequestBody CategoryCreateRequest request) {
        return Result.success(categoryService.create(request));
    }

    /**
     * 根据ID查询分类详情
     *
     * @param id 分类ID（路径参数）
     * @return 分类详细信息
     */
    @GetMapping("/{id}")
    public Result<CategoryDTO> getById(@PathVariable Long id) {
        return Result.success(categoryService.getById(id));
    }

    /**
     * 查询所有分类列表
     *
     * @return 分类列表（按排序号升序）
     */
    @GetMapping("/list")
    public Result<List<CategoryDTO>> listAll() {
        return Result.success(categoryService.listAll());
    }

    /**
     * 更新分类信息
     *
     * @param id      分类ID（路径参数）
     * @param request 更新请求参数（包含新的分类名称、描述、排序号）
     * @return 更新后的分类信息
     */
    @PutMapping("/{id}")
    public Result<CategoryDTO> update(@PathVariable Long id, @RequestBody CategoryUpdateRequest request) {
        return Result.success(categoryService.update(id, request));
    }

    /**
     * 删除分类
     * <p>注意：分类下存在图书时不允许删除</p>
     *
     * @param id 分类ID（路径参数）
     * @return 操作结果
     */
    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        categoryService.delete(id);
        return Result.success();
    }
}
