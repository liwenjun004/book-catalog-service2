package org.book.controller;

import org.book.common.Result;
import org.book.service.BookService;
import org.book.service.dto.BookCreateRequest;
import org.book.service.dto.BookDTO;
import org.book.service.dto.BookUpdateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 图书管理控制器
 * <p>提供图书的RESTful API接口，包括图书的创建、查询、修改和删除操作</p>
 * <p>基础路径：/api/books</p>
 */
@RestController
@RequestMapping("/api/books")
public class BookController {

    /** 图书服务 */
    @Autowired
    private BookService bookService;

    /**
     * 创建新图书
     *
     * @param request 创建请求参数（包含书名、作者、ISBN、价格、分类等信息）
     * @return 创建成功的图书信息
     */
    @PostMapping("/create")
    public Result<BookDTO> create(@RequestBody BookCreateRequest request) {
        return Result.success(bookService.create(request));
    }

    /**
     * 根据ID查询图书详情
     *
     * @param id 图书ID（路径参数）
     * @return 图书详细信息
     */
    @GetMapping("/{id}")
    public Result<BookDTO> getById(@PathVariable Long id) {
        return Result.success(bookService.getById(id));
    }

    /**
     * 查询所有图书列表
     *
     * @return 图书列表
     */
    @GetMapping("/list")
    public Result<List<BookDTO>> listAll() {
        return Result.success(bookService.listAll());
    }

    /**
     * 按分类查询图书列表
     *
     * @param categoryId 分类ID（路径参数）
     * @return 该分类下的图书列表
     */
    @GetMapping("/category/{categoryId}")
    public Result<List<BookDTO>> listByCategoryId(@PathVariable Long categoryId) {
        return Result.success(bookService.listByCategoryId(categoryId));
    }

    /**
     * 更新图书信息
     *
     * @param id      图书ID（路径参数）
     * @param request 更新请求参数（包含新的图书信息）
     * @return 更新后的图书信息
     */
    @PutMapping("/{id}")
    public Result<BookDTO> update(@PathVariable Long id, @RequestBody BookUpdateRequest request) {
        return Result.success(bookService.update(id, request));
    }

    /**
     * 删除图书
     *
     * @param id 图书ID（路径参数）
     * @return 操作结果
     */
    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        bookService.delete(id);
        return Result.success();
    }
}
