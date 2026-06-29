package org.book.infra.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.book.infra.dataobject.BookDO;

import java.util.List;

/**
 * 图书表 Mapper 接口
 * <p>继承 MyBatis-Plus BaseMapper，提供基础的 CRUD 操作</p>
 * <p>自定义 SQL 语句在 BookMapper.xml 中定义</p>
 */
@Mapper
public interface BookMapper extends BaseMapper<BookDO> {

    /**
     * 根据ID查询图书
     *
     * @param id 图书ID
     * @return 图书数据对象
     */
    BookDO selectById(@Param("id") Long id);

    /**
     * 查询所有图书（按创建时间降序）
     *
     * @return 图书列表
     */
    List<BookDO> selectAll();

    /**
     * 根据分类ID查询图书列表
     *
     * @param categoryId 分类ID
     * @return 图书列表
     */
    List<BookDO> selectByCategoryId(@Param("categoryId") Long categoryId);

    /**
     * 插入图书
     *
     * @param book 图书数据对象
     * @return 影响行数
     */
    int insert(BookDO book);

    /**
     * 更新图书
     *
     * @param book 图书数据对象
     * @return 影响行数
     */
    int updateById(BookDO book);

    /**
     * 根据ID删除图书（逻辑删除）
     *
     * @param id 图书ID
     * @return 影响行数
     */
    int deleteById(@Param("id") Long id);

    /**
     * 根据分类ID删除图书（逻辑删除）
     *
     * @param categoryId 分类ID
     * @return 影响行数
     */
    int deleteByCategoryId(@Param("categoryId") Long categoryId);
}
