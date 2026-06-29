package org.book.service.impl;

import org.book.common.BusinessException;
import org.book.domain.book.model.Book;
import org.book.domain.book.repository.BookRepository;
import org.book.domain.category.model.Category;
import org.book.domain.category.repository.CategoryRepository;
import org.book.service.converter.BookServiceConverter;
import org.book.service.dto.BookCreateRequest;
import org.book.service.dto.BookDTO;
import org.book.service.dto.BookUpdateRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServiceImplTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private BookServiceConverter converter;

    @InjectMocks
    private BookServiceImpl bookService;

    private Category testCategory;
    private Book testBook;

    @BeforeEach
    void setUp() {
        testCategory = Category.reconstruct(1L, "文学小说", "中外文学名著", 1,
                LocalDateTime.now(), LocalDateTime.now());
        testBook = Book.reconstruct(1L, "百年孤独", "马尔克斯", "978-7-5442-4528-0",
                "南海出版公司", LocalDate.of(2011, 6, 1),
                new BigDecimal("39.50"), 100, "魔幻现实主义文学代表作", 1L,
                LocalDateTime.now(), LocalDateTime.now());
    }

    @Test
    @DisplayName("创建图书 - 成功")
    void create_success() {
        BookCreateRequest request = new BookCreateRequest();
        request.setTitle("活着");
        request.setAuthor("余华");
        request.setIsbn("978-7-5063-6511-8");
        request.setPublisher("作家出版社");
        request.setPublishDate(LocalDate.of(2012, 8, 1));
        request.setPrice(new BigDecimal("20.00"));
        request.setStock(200);
        request.setDescription("一个人一生的故事");
        request.setCategoryId(1L);

        when(categoryRepository.findById(1L)).thenReturn(testCategory);
        when(converter.toDTO(any(Book.class))).thenAnswer(invocation -> {
            Book book = invocation.getArgument(0);
            BookDTO dto = new BookDTO();
            dto.setTitle(book.getTitle());
            dto.setAuthor(book.getAuthor());
            return dto;
        });

        BookDTO result = bookService.create(request);

        assertNotNull(result);
        assertEquals("活着", result.getTitle());
        assertEquals("余华", result.getAuthor());
        verify(bookRepository).save(any(Book.class));
    }

    @Test
    @DisplayName("创建图书 - 分类不存在")
    void create_categoryNotFound() {
        BookCreateRequest request = new BookCreateRequest();
        request.setTitle("测试");
        request.setCategoryId(999L);

        when(categoryRepository.findById(999L)).thenReturn(null);

        assertThrows(BusinessException.class, () -> bookService.create(request));
        verify(bookRepository, never()).save(any());
    }

    @Test
    @DisplayName("根据ID查询图书 - 成功")
    void getById_success() {
        when(bookRepository.findById(1L)).thenReturn(testBook);
        when(converter.toDTO(testBook)).thenAnswer(invocation -> {
            Book book = invocation.getArgument(0);
            BookDTO dto = new BookDTO();
            dto.setId(book.getId());
            dto.setTitle(book.getTitle());
            return dto;
        });

        BookDTO result = bookService.getById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("百年孤独", result.getTitle());
    }

    @Test
    @DisplayName("根据ID查询图书 - 不存在")
    void getById_notFound() {
        when(bookRepository.findById(999L)).thenReturn(null);

        assertThrows(BusinessException.class, () -> bookService.getById(999L));
    }

    @Test
    @DisplayName("查询所有图书")
    void listAll() {
        when(bookRepository.findAll()).thenReturn(Collections.singletonList(testBook));
        when(converter.toDTOList(anyList())).thenReturn(Collections.emptyList());

        List<BookDTO> result = bookService.listAll();

        assertNotNull(result);
        verify(bookRepository).findAll();
    }

    @Test
    @DisplayName("按分类查询图书 - 成功")
    void listByCategoryId_success() {
        when(categoryRepository.findById(1L)).thenReturn(testCategory);
        when(bookRepository.findByCategoryId(1L)).thenReturn(Collections.singletonList(testBook));
        when(converter.toDTOList(anyList())).thenReturn(Collections.emptyList());

        List<BookDTO> result = bookService.listByCategoryId(1L);

        assertNotNull(result);
        verify(bookRepository).findByCategoryId(1L);
    }

    @Test
    @DisplayName("按分类查询图书 - 分类不存在")
    void listByCategoryId_categoryNotFound() {
        when(categoryRepository.findById(999L)).thenReturn(null);

        assertThrows(BusinessException.class, () -> bookService.listByCategoryId(999L));
    }

    @Test
    @DisplayName("更新图书 - 成功")
    void update_success() {
        BookUpdateRequest request = new BookUpdateRequest();
        request.setTitle("百年孤独(新版)");
        request.setAuthor("马尔克斯");
        request.setIsbn("978-7-5442-4528-0");
        request.setPublisher("南海出版公司");
        request.setPublishDate(LocalDate.of(2011, 6, 1));
        request.setPrice(new BigDecimal("45.00"));
        request.setStock(150);
        request.setDescription("更新后的描述");
        request.setCategoryId(1L);

        when(bookRepository.findById(1L)).thenReturn(testBook);
        when(categoryRepository.findById(1L)).thenReturn(testCategory);
        when(converter.toDTO(any(Book.class))).thenAnswer(invocation -> {
            Book book = invocation.getArgument(0);
            BookDTO dto = new BookDTO();
            dto.setTitle(book.getTitle());
            dto.setPrice(book.getPrice());
            return dto;
        });

        BookDTO result = bookService.update(1L, request);

        assertNotNull(result);
        assertEquals("百年孤独(新版)", result.getTitle());
        assertEquals(new BigDecimal("45.00"), result.getPrice());
        verify(bookRepository).update(any(Book.class));
    }

    @Test
    @DisplayName("更新图书 - 不存在")
    void update_notFound() {
        BookUpdateRequest request = new BookUpdateRequest();
        request.setCategoryId(1L);

        when(bookRepository.findById(999L)).thenReturn(null);

        assertThrows(BusinessException.class, () -> bookService.update(999L, request));
    }

    @Test
    @DisplayName("更新图书 - 分类不存在")
    void update_categoryNotFound() {
        BookUpdateRequest request = new BookUpdateRequest();
        request.setCategoryId(999L);

        when(bookRepository.findById(1L)).thenReturn(testBook);
        when(categoryRepository.findById(999L)).thenReturn(null);

        assertThrows(BusinessException.class, () -> bookService.update(1L, request));
    }

    @Test
    @DisplayName("删除图书 - 成功")
    void delete_success() {
        when(bookRepository.findById(1L)).thenReturn(testBook);

        assertDoesNotThrow(() -> bookService.delete(1L));
        verify(bookRepository).deleteById(1L);
    }

    @Test
    @DisplayName("删除图书 - 不存在")
    void delete_notFound() {
        when(bookRepository.findById(999L)).thenReturn(null);

        assertThrows(BusinessException.class, () -> bookService.delete(999L));
    }
}
