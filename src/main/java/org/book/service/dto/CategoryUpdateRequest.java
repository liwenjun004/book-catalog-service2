package org.book.service.dto;

import lombok.Data;

/**
 * 更新分类请求参数
 * <p>用于接收前端更新分类信息时提交的数据</p>
 */
@Data
public class CategoryUpdateRequest {

    /** 分类名称（必填，最长100字符） */
    private String name;

    /** 分类描述（可选，最长500字符） */
    private String description;

    /** 排序号（可选） */
    private Integer sortOrder;
}
