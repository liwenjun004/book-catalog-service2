package org.book.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.book.common.BusinessException;
import org.book.common.GlobalExceptionHandler;
import org.book.service.BookService;
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
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class BookControllerTest {

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @Mock
    private BookService bookService;

    @InjectMocks
    private BookController bookController;

    private static final LocalDateTime NOW = LocalDateTime.of(2024, 1, 1, 12, 0, 0);

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(bookController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    private BookDTO buildBookDTO() {
        BookDTO dto = new BookDTO();
        dto.setId(1L);
        dto.setTitle("百年孤独");
        dto.setAuthor("马尔克斯");
        dto.setIsbn("978-7-5442-4528-0");
        dto.setPublisher("南海出版公司");
        dto.setPublishDate(LocalDate.of(2011, 6, 1));
        dto.setPrice(new BigDecimal("39.50"));
        dto.setStock(100);
        dto.setDescription("魔幻现实主义文学代表作");
        dto.setCategoryId(1L);
        dto.setCreatedAt(NOW);
        dto.setUpdatedAt(NOW);
        return dto;
    }

    @Test
    @DisplayName("POST /api/books/create - 创建图书成功")
    void create_success() throws Exception {
        BookDTO dto = buildBookDTO();
        when(bookService.create(any(BookCreateRequest.class))).thenReturn(dto);

        BookCreateRequest request = new BookCreateRequest();
        request.setTitle("百年孤独");
        request.setAuthor("马尔克斯");
        request.setIsbn("978-7-5442-4528-0");
        request.setPublisher("南海出版公司");
        request.setPublishDate(LocalDate.of(2011, 6, 1));
        request.setPrice(new BigDecimal("39.50"));
        request.setStock(100);
        request.setDescription("魔幻现实主义文学代表作");
        request.setCategoryId(1L);

        mockMvc.perform(post("/api/books/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("success"))
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.title").value("百年孤独"))
                .andExpect(jsonPath("$.data.author").value("马尔克斯"))
                .andExpect(jsonPath("$.data.isbn").value("978-7-5442-4528-0"))
                .andExpect(jsonPath("$.data.price").value(39.50))
                .andExpect(jsonPath("$.data.stock").value(100))
                .andExpect(jsonPath("$.data.categoryId").value(1));

        verify(bookService).create(any(BookCreateRequest.class));
    }

    @Test
    @DisplayName("POST /api/books/create - 书名为空返回错误")
    void create_invalidTitle() throws Exception {
        when(bookService.create(any(BookCreateRequest.class)))
                .thenThrow(new BusinessException("书名不能为空"));

        BookCreateRequest request = new BookCreateRequest();
        request.setTitle("");
        request.setCategoryId(1L);

        mockMvc.perform(post("/api/books/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("书名不能为空"));
    }

    @Test
    @DisplayName("POST /api/books/create - 分类不存在返回错误")
    void create_categoryNotFound() throws Exception {
        when(bookService.create(any(BookCreateRequest.class)))
                .thenThrow(new BusinessException("分类不存在, categoryId=999"));

        BookCreateRequest request = new BookCreateRequest();
        request.setTitle("测试");
        request.setCategoryId(999L);

        mockMvc.perform(post("/api/books/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("分类不存在, categoryId=999"));
    }

    @Test
    @DisplayName("POST /api/books/create - 价格为负数返回错误")
    void create_negativePrice() throws Exception {
        when(bookService.create(any(BookCreateRequest.class)))
                .thenThrow(new BusinessException("价格不能为负数"));

        BookCreateRequest request = new BookCreateRequest();
        request.setTitle("测试");
        request.setPrice(new BigDecimal("-10"));
        request.setCategoryId(1L);

        mockMvc.perform(post("/api/books/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("价格不能为负数"));
    }

    @Test
    @DisplayName("GET /api/books/{id} - 根据ID查询图书成功")
    void getById_success() throws Exception {
        BookDTO dto = buildBookDTO();
        when(bookService.getById(1L)).thenReturn(dto);

        mockMvc.perform(get("/api/books/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.title").value("百年孤独"))
                .andExpect(jsonPath("$.data.author").value("马尔克斯"))
                .andExpect(jsonPath("$.data.publisher").value("南海出版公司"));
    }

    @Test
    @DisplayName("GET /api/books/{id} - 图书不存在")
    void getById_notFound() throws Exception {
        when(bookService.getById(999L))
                .thenThrow(new BusinessException("图书不存在, id=999"));

        mockMvc.perform(get("/api/books/999"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("图书不存在, id=999"));
    }

    @Test
    @DisplayName("GET /api/books/list - 查询所有图书")
    void listAll() throws Exception {
        BookDTO dto1 = buildBookDTO();

        BookDTO dto2 = new BookDTO();
        dto2.setId(2L);
        dto2.setTitle("活着");
        dto2.setAuthor("余华");
        dto2.setCategoryId(1L);
        dto2.setCreatedAt(NOW);
        dto2.setUpdatedAt(NOW);

        when(bookService.listAll()).thenReturn(Arrays.asList(dto1, dto2));

        mockMvc.perform(get("/api/books/list"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data.length()").value(2))
                .andExpect(jsonPath("$.data[0].title").value("百年孤独"))
                .andExpect(jsonPath("$.data[1].title").value("活着"));
    }

    @Test
    @DisplayName("GET /api/books/list - 无图书数据返回空列表")
    void listAll_empty() throws Exception {
        when(bookService.listAll()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/books/list"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data.length()").value(0));
    }

    @Test
    @DisplayName("GET /api/books/category/{categoryId} - 按分类查询图书")
    void listByCategoryId_success() throws Exception {
        BookDTO dto = buildBookDTO();
        when(bookService.listByCategoryId(1L)).thenReturn(Collections.singletonList(dto));

        mockMvc.perform(get("/api/books/category/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data.length()").value(1))
                .andExpect(jsonPath("$.data[0].title").value("百年孤独"))
                .andExpect(jsonPath("$.data[0].categoryId").value(1));
    }

    @Test
    @DisplayName("GET /api/books/category/{categoryId} - 分类不存在")
    void listByCategoryId_categoryNotFound() throws Exception {
        when(bookService.listByCategoryId(999L))
                .thenThrow(new BusinessException("分类不存在, categoryId=999"));

        mockMvc.perform(get("/api/books/category/999"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("分类不存在, categoryId=999"));
    }

    @Test
    @DisplayName("GET /api/books/category/{categoryId} - 分类下无图书返回空列表")
    void listByCategoryId_empty() throws Exception {
        when(bookService.listByCategoryId(1L)).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/books/category/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data.length()").value(0));
    }

    @Test
    @DisplayName("PUT /api/books/{id} - 更新图书成功")
    void update_success() throws Exception {
        BookDTO dto = buildBookDTO();
        when(bookService.update(eq(1L), any(BookUpdateRequest.class))).thenReturn(dto);

        BookUpdateRequest request = new BookUpdateRequest();
        request.setTitle("百年孤独");
        request.setAuthor("马尔克斯");
        request.setIsbn("978-7-5442-4528-0");
        request.setPublisher("南海出版公司");
        request.setPublishDate(LocalDate.of(2011, 6, 1));
        request.setPrice(new BigDecimal("39.50"));
        request.setStock(100);
        request.setDescription("魔幻现实主义文学代表作");
        request.setCategoryId(1L);

        mockMvc.perform(put("/api/books/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data.title").value("百年孤独"));

        verify(bookService).update(eq(1L), any(BookUpdateRequest.class));
    }

    @Test
    @DisplayName("PUT /api/books/{id} - 图书不存在")
    void update_notFound() throws Exception {
        when(bookService.update(eq(999L), any(BookUpdateRequest.class)))
                .thenThrow(new BusinessException("图书不存在, id=999"));

        BookUpdateRequest request = new BookUpdateRequest();
        request.setTitle("新书名");
        request.setCategoryId(1L);

        mockMvc.perform(put("/api/books/999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("图书不存在, id=999"));
    }

    @Test
    @DisplayName("PUT /api/books/{id} - 分类不存在")
    void update_categoryNotFound() throws Exception {
        when(bookService.update(eq(1L), any(BookUpdateRequest.class)))
                .thenThrow(new BusinessException("分类不存在, categoryId=999"));

        BookUpdateRequest request = new BookUpdateRequest();
        request.setTitle("百年孤独");
        request.setCategoryId(999L);

        mockMvc.perform(put("/api/books/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("分类不存在, categoryId=999"));
    }

    @Test
    @DisplayName("DELETE /api/books/{id} - 删除图书成功")
    void delete_success() throws Exception {
        doNothing().when(bookService).delete(1L);

        mockMvc.perform(delete("/api/books/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("success"));

        verify(bookService).delete(1L);
    }

    @Test
    @DisplayName("DELETE /api/books/{id} - 图书不存在")
    void delete_notFound() throws Exception {
        doThrow(new BusinessException("图书不存在, id=999"))
                .when(bookService).delete(999L);

        mockMvc.perform(delete("/api/books/999"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("图书不存在, id=999"));
    }
}
