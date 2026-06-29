package org.book.infra.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.book.infra.dataobject.CategoryDO;

import java.util.List;

/**
 * 分类表 Mapper 接口
 * <p>继承 MyBatis-Plus BaseMapper，提供基础的 CRUD 操作</p>
 * <p>自定义 SQL 语句在 CategoryMapper.xml 中定义</p>
 */
@Mapper
public interface CategoryMapper extends BaseMapper<CategoryDO> {

    /**
     * 根据ID查询分类
     *
     * @param id 分类ID
     * @return 分类数据对象
     */
    CategoryDO selectById(@Param("id") Long id);

    /**
     * 查询所有分类（按排序号升序）
     *
     * @return 分类列表
     */
    List<CategoryDO> selectAll();

    /**
     * 根据名称查询分类
     *
     * @param name 分类名称
     * @return 分类数据对象
     */
    CategoryDO selectByName(@Param("name") String name);

    /**
     * 检查名称是否存在（排除指定ID）
     *
     * @param name 分类名称
     * @param id   排除的分类ID
     * @return true表示存在
     */
    boolean existsByNameAndIdNot(@Param("name") String name, @Param("id") Long id);

    /**
     * 插入分类
     *
     * @param category 分类数据对象
     * @return 影响行数
     */
    int insert(CategoryDO category);

    /**
     * 更新分类
     *
     * @param category 分类数据对象
     * @return 影响行数
     */
    int updateById(CategoryDO category);

    /**
     * 根据ID删除分类（逻辑删除）
     *
     * @param id 分类ID
     * @return 影响行数
     */
    int deleteById(@Param("id") Long id);
}
