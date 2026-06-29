package org.book.service.impl;

import org.book.common.BusinessException;
import org.book.domain.book.model.Book;
import org.book.domain.book.repository.BookRepository;
import org.book.domain.category.model.Category;
import org.book.domain.category.repository.CategoryRepository;
import org.book.service.converter.CategoryServiceConverter;
import org.book.service.dto.CategoryCreateRequest;
import org.book.service.dto.CategoryDTO;
import org.book.service.dto.CategoryUpdateRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryServiceImplTest {

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private CategoryServiceConverter converter;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    private Category testCategory;

    @BeforeEach
    void setUp() {
        testCategory = Category.reconstruct(1L, "文学小说", "中外文学名著", 1,
                LocalDateTime.now(), LocalDateTime.now());
    }

    @Test
    @DisplayName("创建分类 - 成功")
    void create_success() {
        CategoryCreateRequest request = new CategoryCreateRequest();
        request.setName("科技");
        request.setDescription("科技类书籍");
        request.setSortOrder(2);

        when(categoryRepository.existsByName("科技")).thenReturn(false);
        when(converter.toDTO(any(Category.class))).thenAnswer(invocation -> {
            Category cat = invocation.getArgument(0);
            CategoryDTO dto = new CategoryDTO();
            dto.setName(cat.getName());
            dto.setDescription(cat.getDescription());
            dto.setSortOrder(cat.getSortOrder());
            return dto;
        });

        CategoryDTO result = categoryService.create(request);

        assertNotNull(result);
        assertEquals("科技", result.getName());
        assertEquals("科技类书籍", result.getDescription());
        assertEquals(2, result.getSortOrder());
        verify(categoryRepository).save(any(Category.class));
    }

    @Test
    @DisplayName("创建分类 - 名称已存在")
    void create_nameExists() {
        CategoryCreateRequest request = new CategoryCreateRequest();
        request.setName("文学小说");

        when(categoryRepository.existsByName("文学小说")).thenReturn(true);

        assertThrows(BusinessException.class, () -> categoryService.create(request));
        verify(categoryRepository, never()).save(any());
    }

    @Test
    @DisplayName("根据ID查询分类 - 成功")
    void getById_success() {
        when(categoryRepository.findById(1L)).thenReturn(testCategory);
        when(converter.toDTO(testCategory)).thenAnswer(invocation -> {
            Category cat = invocation.getArgument(0);
            CategoryDTO dto = new CategoryDTO();
            dto.setId(cat.getId());
            dto.setName(cat.getName());
            return dto;
        });

        CategoryDTO result = categoryService.getById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("文学小说", result.getName());
    }

    @Test
    @DisplayName("根据ID查询分类 - 不存在")
    void getById_notFound() {
        when(categoryRepository.findById(999L)).thenReturn(null);

        assertThrows(BusinessException.class, () -> categoryService.getById(999L));
    }

    @Test
    @DisplayName("查询所有分类")
    void listAll() {
        Category cat2 = Category.reconstruct(2L, "科技", "科技类", 2,
                LocalDateTime.now(), LocalDateTime.now());
        when(categoryRepository.findAll()).thenReturn(Arrays.asList(testCategory, cat2));
        when(converter.toDTOList(anyList())).thenReturn(Collections.emptyList());

        List<CategoryDTO> result = categoryService.listAll();

        assertNotNull(result);
        verify(categoryRepository).findAll();
    }

    @Test
    @DisplayName("更新分类 - 成功")
    void update_success() {
        CategoryUpdateRequest request = new CategoryUpdateRequest();
        request.setName("文学名著");
        request.setDescription("更新后的描述");
        request.setSortOrder(1);

        when(categoryRepository.findById(1L)).thenReturn(testCategory);
        when(categoryRepository.existsByNameAndIdNot("文学名著", 1L)).thenReturn(false);
        when(converter.toDTO(any(Category.class))).thenAnswer(invocation -> {
            Category cat = invocation.getArgument(0);
            CategoryDTO dto = new CategoryDTO();
            dto.setName(cat.getName());
            return dto;
        });

        CategoryDTO result = categoryService.update(1L, request);

        assertNotNull(result);
        assertEquals("文学名著", result.getName());
        verify(categoryRepository).update(any(Category.class));
    }

    @Test
    @DisplayName("更新分类 - 不存在")
    void update_notFound() {
        CategoryUpdateRequest request = new CategoryUpdateRequest();
        request.setName("新名称");

        when(categoryRepository.findById(999L)).thenReturn(null);

        assertThrows(BusinessException.class, () -> categoryService.update(999L, request));
    }

    @Test
    @DisplayName("更新分类 - 名称重复")
    void update_nameDuplicate() {
        CategoryUpdateRequest request = new CategoryUpdateRequest();
        request.setName("科技");

        when(categoryRepository.findById(1L)).thenReturn(testCategory);
        when(categoryRepository.existsByNameAndIdNot("科技", 1L)).thenReturn(true);

        assertThrows(BusinessException.class, () -> categoryService.update(1L, request));
    }

    @Test
    @DisplayName("删除分类 - 成功")
    void delete_success() {
        when(categoryRepository.findById(1L)).thenReturn(testCategory);
        when(bookRepository.findByCategoryId(1L)).thenReturn(Collections.emptyList());

        assertDoesNotThrow(() -> categoryService.delete(1L));
        verify(categoryRepository).deleteById(1L);
    }

    @Test
    @DisplayName("删除分类 - 不存在")
    void delete_notFound() {
        when(categoryRepository.findById(999L)).thenReturn(null);

        assertThrows(BusinessException.class, () -> categoryService.delete(999L));
    }

    @Test
    @DisplayName("删除分类 - 分类下有图书")
    void delete_hasBooks() {
        Book book = Book.reconstruct(1L, "百年孤独", "马尔克斯", null, null, null,
                null, 100, null, 1L, LocalDateTime.now(), LocalDateTime.now());

        when(categoryRepository.findById(1L)).thenReturn(testCategory);
        when(bookRepository.findByCategoryId(1L)).thenReturn(Collections.singletonList(book));

        assertThrows(BusinessException.class, () -> categoryService.delete(1L));
        verify(categoryRepository, never()).deleteById(anyLong());
    }
}
