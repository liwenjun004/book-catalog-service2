package org.book.infra.dataobject;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 分类数据对象
 * <p>对应数据库 category 表，用于 MyBatis-Plus 的 ORM 映射</p>
 */
@Data
@TableName("category")
public class CategoryDO {

    /** 分类ID（主键，自增） */
    @TableId(type = IdType.AUTO)
    private Long id;

    /** 分类名称 */
    private String name;

    /** 分类描述 */
    private String description;

    /** 排序号 */
    private Integer sortOrder;

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
