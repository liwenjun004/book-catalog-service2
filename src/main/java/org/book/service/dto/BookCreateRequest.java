package org.book.service.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 创建图书请求参数
 * <p>用于接收前端创建图书时提交的数据</p>
 */
@Data
public class BookCreateRequest {

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

    /** 库存数量（可选，默认为0，不能为负数） */
    private Integer stock;

    /** 图书简介（可选） */
    private String description;

    /** 所属分类ID（必填，必须为已存在的分类） */
    private Long categoryId;
}
