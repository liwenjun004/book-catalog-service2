package org.book.domain.book.repository;

import org.book.domain.book.model.Book;

import java.util.List;

/**
 * 图书仓储接口
 * <p>定义图书领域的数据访问操作，由基础设施层实现</p>
 */
public interface BookRepository {

    /**
     * 根据ID查询图书
     *
     * @param id 图书ID
     * @return 图书领域对象，不存在时返回null
     */
    Book findById(Long id);

    /**
     * 根据分类ID查询该分类下的所有图书
     *
     * @param categoryId 分类ID
     * @return 图书列表
     */
    List<Book> findByCategoryId(Long categoryId);

    /**
     * 查询所有图书
     *
     * @return 图书列表
     */
    List<Book> findAll();

    /**
     * 保存新图书
     *
     * @param book 图书领域对象
     */
    void save(Book book);

    /**
     * 更新图书信息
     *
     * @param book 图书领域对象
     */
    void update(Book book);

    /**
     * 根据ID删除图书
     *
     * @param id 图书ID
     */
    void deleteById(Long id);

    /**
     * 根据分类ID删除该分类下的所有图书
     *
     * @param categoryId 分类ID
     */
    void deleteByCategoryId(Long categoryId);
}
