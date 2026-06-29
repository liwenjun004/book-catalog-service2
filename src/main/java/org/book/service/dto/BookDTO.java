package org.book.service.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 图书响应数据传输对象
 * <p>用于向前端返回图书详细信息</p>
 */
@Data
public class BookDTO {

    /** 图书ID */
    private Long id;

    /** 书名 */
    private String title;

    /** 作者 */
    private String author;

    /** ISBN编号 */
    private String isbn;

    /** 出版社 */
    private String publisher;

    /** 出版日期 */
    private LocalDate publishDate;

    /** 价格 */
    private BigDecimal price;

    /** 库存数量 */
    private Integer stock;

    /** 图书简介 */
    private String description;

    /** 所属分类ID */
    private Long categoryId;

    /** 创建时间 */
    private LocalDateTime createdAt;

    /** 更新时间 */
    private LocalDateTime updatedAt;
}
