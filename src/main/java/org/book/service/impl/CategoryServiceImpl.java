package org.book.service.impl;

import lombok.RequiredArgsConstructor;
import org.book.common.BusinessException;
import org.book.domain.category.model.Category;
import org.book.domain.category.repository.CategoryRepository;
import org.book.domain.book.repository.BookRepository;
import org.book.service.CategoryService;
import org.book.service.converter.CategoryServiceConverter;
import org.book.service.dto.CategoryCreateRequest;
import org.book.service.dto.CategoryDTO;
import org.book.service.dto.CategoryUpdateRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 分类服务实现类
 * <p>提供分类的创建、查询、修改、删除等业务操作</p>
 */
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final BookRepository bookRepository;
    private final CategoryServiceConverter converter;

    @Override
    @Transactional
    public CategoryDTO create(CategoryCreateRequest request) {
        if (categoryRepository.existsByName(request.getName())) {
            throw new BusinessException("分类名称已存在: " + request.getName());
        }
        Category category = Category.create(
                request.getName(),
                request.getDescription(),
                request.getSortOrder()
        );
        categoryRepository.save(category);
        return converter.toDTO(category);
    }

    @Override
    public CategoryDTO getById(Long id) {
        Category category = categoryRepository.findById(id);
        if (category == null) {
            throw new BusinessException("分类不存在, id=" + id);
        }
        return converter.toDTO(category);
    }

    @Override
    public List<CategoryDTO> listAll() {
        return converter.toDTOList(categoryRepository.findAll());
    }

    @Override
    @Transactional
    public CategoryDTO update(Long id, CategoryUpdateRequest request) {
        Category category = categoryRepository.findById(id);
        if (category == null) {
            throw new BusinessException("分类不存在, id=" + id);
        }
        if (categoryRepository.existsByNameAndIdNot(request.getName(), id)) {
            throw new BusinessException("分类名称已存在: " + request.getName());
        }
        category.update(request.getName(), request.getDescription(), request.getSortOrder());
        categoryRepository.update(category);
        return converter.toDTO(category);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Category category = categoryRepository.findById(id);
        if (category == null) {
            throw new BusinessException("分类不存在, id=" + id);
        }
        if (!bookRepository.findByCategoryId(id).isEmpty()) {
            throw new BusinessException("该分类下存在图书，无法删除");
        }
        categoryRepository.deleteById(id);
    }
}
