package org.book.service;

import org.book.service.dto.BookCreateRequest;
import org.book.service.dto.BookDTO;
import org.book.service.dto.BookUpdateRequest;

import java.util.List;

/**
 * 图书服务接口
 * <p>提供图书的创建、查询、修改、删除等业务操作</p>
 */
public interface BookService {

    /**
     * 创建新图书
     *
     * @param request 创建请求参数
     * @return 创建成功的图书信息
     * @throws org.book.common.BusinessException 分类不存在或参数校验失败时抛出
     */
    BookDTO create(BookCreateRequest request);

    /**
     * 根据ID查询图书详情
     *
     * @param id 图书ID
     * @return 图书信息
     * @throws org.book.common.BusinessException 图书不存在时抛出
     */
    BookDTO getById(Long id);

    /**
     * 查询所有图书列表
     *
     * @return 图书列表
     */
    List<BookDTO> listAll();

    /**
     * 根据分类ID查询该分类下的所有图书
     *
     * @param categoryId 分类ID
     * @return 图书列表
     * @throws org.book.common.BusinessException 分类不存在时抛出
     */
    List<BookDTO> listByCategoryId(Long categoryId);

    /**
     * 更新图书信息
     *
     * @param id      图书ID
     * @param request 更新请求参数
     * @return 更新后的图书信息
     * @throws org.book.common.BusinessException 图书或分类不存在时抛出
     */
    BookDTO update(Long id, BookUpdateRequest request);

    /**
     * 删除图书
     *
     * @param id 图书ID
     * @throws org.book.common.BusinessException 图书不存在时抛出
     */
    void delete(Long id);
}
