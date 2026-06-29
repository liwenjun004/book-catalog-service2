package org.book.infra.dataobject;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 图书数据对象
 * <p>对应数据库 book 表，用于 MyBatis-Plus 的 ORM 映射</p>
 */
@Data
@TableName("book")
public class BookDO {

    /** 图书ID（主键，自增） */
    @TableId(type = IdType.AUTO)
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

    /** 所属分类ID（外键） */
    private Long categoryId;

    /** 创建时间（自动填充） */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdAt;

    /** 最后更新时间（自动填充） */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updatedAt;

    /** 逻辑删除标志（0-未删除，1-已删除） */
    @TableLogic
    private Integer deleted;
}
