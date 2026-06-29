package org.book.infra.repository;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.RequiredArgsConstructor;
import org.book.domain.category.model.Category;
import org.book.domain.category.repository.CategoryRepository;
import org.book.infra.converter.CategoryConverter;
import org.book.infra.dataobject.CategoryDO;
import org.book.infra.mapper.CategoryMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 分类仓储实现类
 * <p>基于 MyBatis-Plus 实现分类的数据访问操作</p>
 */
@Repository
@RequiredArgsConstructor
public class CategoryRepositoryImpl implements CategoryRepository {

    private final CategoryMapper categoryMapper;
    private final CategoryConverter categoryConverter;

    @Override
    public Category findById(Long id) {
        CategoryDO doObj = categoryMapper.selectById(id);
        return categoryConverter.toDomain(doObj);
    }

    @Override
    public List<Category> findAll() {
        LambdaQueryWrapper<CategoryDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByAsc(CategoryDO::getSortOrder);
        return categoryMapper.selectList(wrapper).stream()
                .map(categoryConverter::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void save(Category category) {
        CategoryDO doObj = categoryConverter.toDataObject(category);
        categoryMapper.insert(doObj);
    }

    @Override
    public void update(Category category) {
        CategoryDO doObj = categoryConverter.toDataObject(category);
        categoryMapper.updateById(doObj);
    }

    @Override
    public void deleteById(Long id) {
        categoryMapper.deleteById(id);
    }

    @Override
    public boolean existsByName(String name) {
        LambdaQueryWrapper<CategoryDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CategoryDO::getName, name);
        return categoryMapper.selectCount(wrapper) > 0;
    }

    @Override
    public boolean existsByNameAndIdNot(String name, Long id) {
        LambdaQueryWrapper<CategoryDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CategoryDO::getName, name);
        wrapper.ne(CategoryDO::getId, id);
        return categoryMapper.selectCount(wrapper) > 0;
    }
}
