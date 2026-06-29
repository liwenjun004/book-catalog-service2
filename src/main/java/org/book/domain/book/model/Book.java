package org.book.domain.book.model;

import lombok.Getter;
import org.book.common.BusinessException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 图书领域实体（充血模型）
 * <p>封装图书的核心业务逻辑，包括创建、更新、库存变更等操作</p>
 * <p>使用私有构造器 + 静态工厂方法，确保对象创建的合法性</p>
 */
@Getter
public class Book {

    /** 图书ID（主键） */
    private Long id;

    /** 书名（必填，最长200字符） */
    private String title;

    /** 作者（可选） */
    private String author;

    /** ISBN编号（可选） */
    private String isbn;

    /** 出版社（可选） */
    private String publisher;

    /** 出版日期（可选） */
    private LocalDate publishDate;

    /** 价格（可选，不能为负数） */
    private BigDecimal price;

    /** 库存数量（默认为0，不能为负数） */
    private Integer stock;

    /** 图书简介（可选） */
    private String description;

    /** 所属分类ID（必填，关联分类表） */
    private Long categoryId;

    /** 创建时间 */
    private LocalDateTime createdAt;

    /** 最后更新时间 */
    private LocalDateTime updatedAt;

    /** 私有构造器，通过静态工厂方法创建实例 */
    private Book() {}

    /**
     * 工厂方法：创建新书籍
     */
    public static Book create(String title, String author, String isbn, String publisher,
                              LocalDate publishDate, BigDecimal price, Integer stock,
                              String description, Long categoryId) {
        Book book = new Book();
        book.validateTitle(title);
        book.validatePrice(price);
        book.validateStock(stock);
        book.title = title;
        book.author = author;
        book.isbn = isbn;
        book.publisher = publisher;
        book.publishDate = publishDate;
        book.price = price;
        book.stock = stock != null ? stock : 0;
        book.description = description;
        book.categoryId = categoryId;
        book.createdAt = LocalDateTime.now();
        book.updatedAt = LocalDateTime.now();
        return book;
    }

    /**
     * 工厂方法：从持久化重建
     */
    public static Book reconstruct(Long id, String title, String author, String isbn,
                                   String publisher, LocalDate publishDate, BigDecimal price,
                                   Integer stock, String description, Long categoryId,
                                   LocalDateTime createdAt, LocalDateTime updatedAt) {
        Book book = new Book();
        book.id = id;
        book.title = title;
        book.author = author;
        book.isbn = isbn;
        book.publisher = publisher;
        book.publishDate = publishDate;
        book.price = price;
        book.stock = stock;
        book.description = description;
        book.categoryId = categoryId;
        book.createdAt = createdAt;
        book.updatedAt = updatedAt;
        return book;
    }

    /**
     * 更新图书信息
     *
     * @param title       新书名
     * @param author      新作者
     * @param isbn        新ISBN
     * @param publisher   新出版社
     * @param publishDate 新出版日期
     * @param price       新价格
     * @param stock       新库存数量
     * @param description 新图书简介
     * @param categoryId  新所属分类ID
     * @throws BusinessException 参数校验失败时抛出
     */
    public void update(String title, String author, String isbn, String publisher,
                       LocalDate publishDate, BigDecimal price, Integer stock,
                       String description, Long categoryId) {
        validateTitle(title);
        validatePrice(price);
        validateStock(stock);
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.publisher = publisher;
        this.publishDate = publishDate;
        this.price = price;
        this.stock = stock;
        this.description = description;
        this.categoryId = categoryId;
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * 调整库存数量（正数表示入库，负数表示出库）
     *
     * @param quantity 调整数量（正数入库，负数出库）
     * @throws BusinessException 库存不足时抛出
     */
    public void updateStock(int quantity) {
        int newStock = this.stock + quantity;
        if (newStock < 0) {
            throw new BusinessException("库存不足，当前库存: " + this.stock);
        }
        this.stock = newStock;
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * 判断该图书是否属于指定分类
     *
     * @param categoryId 分类ID
     * @return true表示属于该分类
     */
    public boolean belongsToCategory(Long categoryId) {
        return this.categoryId != null && this.categoryId.equals(categoryId);
    }

    /**
     * 校验书名合法性
     *
     * @param title 待校验的书名
     * @throws BusinessException 书名为空或超长时抛出
     */
    private void validateTitle(String title) {
        if (title == null || title.trim().isEmpty()) {
            throw new BusinessException("书名不能为空");
        }
        if (title.length() > 200) {
            throw new BusinessException("书名不能超过200个字符");
        }
    }

    /**
     * 校验价格合法性
     *
     * @param price 待校验的价格
     * @throws BusinessException 价格为负数时抛出
     */
    private void validatePrice(BigDecimal price) {
        if (price != null && price.compareTo(BigDecimal.ZERO) < 0) {
            throw new BusinessException("价格不能为负数");
        }
    }

    /**
     * 校验库存数量合法性
     *
     * @param stock 待校验的库存数量
     * @throws BusinessException 库存为负数时抛出
     */
    private void validateStock(Integer stock) {
        if (stock != null && stock < 0) {
            throw new BusinessException("库存不能为负数");
        }
    }
}
