package org.book.service.dto;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 分类响应数据传输对象
 * <p>用于向前端返回分类信息</p>
 */
@Data
public class CategoryDTO {

    /** 分类ID */
    private Long id;

    /** 分类名称 */
    private String name;

    /** 分类描述 */
    private String description;

    /** 排序号 */
    private Integer sortOrder;

    /** 创建时间 */
    private LocalDateTime createdAt;

    /** 更新时间 */
    private LocalDateTime updatedAt;
}
