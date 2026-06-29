package org.book.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.book.common.BusinessException;
import org.book.common.GlobalExceptionHandler;
import org.book.service.CategoryService;
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
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class CategoryControllerTest {

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @Mock
    private CategoryService categoryService;

    @InjectMocks
    private CategoryController categoryController;

    private static final LocalDateTime NOW = LocalDateTime.of(2024, 1, 1, 12, 0, 0);

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(categoryController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    @DisplayName("POST /api/categories/create - 创建分类成功")
    void create_success() throws Exception {
        CategoryDTO dto = new CategoryDTO();
        dto.setId(1L);
        dto.setName("文学小说");
        dto.setDescription("中外文学名著");
        dto.setSortOrder(1);
        dto.setCreatedAt(NOW);
        dto.setUpdatedAt(NOW);

        when(categoryService.create(any(CategoryCreateRequest.class))).thenReturn(dto);

        CategoryCreateRequest request = new CategoryCreateRequest();
        request.setName("文学小说");
        request.setDescription("中外文学名著");
        request.setSortOrder(1);

        mockMvc.perform(post("/api/categories/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("success"))
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.name").value("文学小说"))
                .andExpect(jsonPath("$.data.description").value("中外文学名著"))
                .andExpect(jsonPath("$.data.sortOrder").value(1));

        verify(categoryService).create(any(CategoryCreateRequest.class));
    }

    @Test
    @DisplayName("POST /api/categories/create - 分类名称为空返回错误")
    void create_invalidRequest() throws Exception {
        when(categoryService.create(any(CategoryCreateRequest.class)))
                .thenThrow(new BusinessException("分类名称不能为空"));

        CategoryCreateRequest request = new CategoryCreateRequest();
        request.setName("");

        mockMvc.perform(post("/api/categories/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(500))
                .andExpect(jsonPath("$.message").value("分类名称不能为空"));
    }

    @Test
    @DisplayName("POST /api/categories/create - 分类名称重复返回错误")
    void create_duplicateName() throws Exception {
        when(categoryService.create(any(CategoryCreateRequest.class)))
                .thenThrow(new BusinessException("分类名称已存在: 文学小说"));

        CategoryCreateRequest request = new CategoryCreateRequest();
        request.setName("文学小说");

        mockMvc.perform(post("/api/categories/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("分类名称已存在: 文学小说"));
    }

    @Test
    @DisplayName("GET /api/categories/{id} - 根据ID查询分类成功")
    void getById_success() throws Exception {
        CategoryDTO dto = new CategoryDTO();
        dto.setId(1L);
        dto.setName("文学小说");
        dto.setDescription("中外文学名著");
        dto.setSortOrder(1);
        dto.setCreatedAt(NOW);
        dto.setUpdatedAt(NOW);

        when(categoryService.getById(1L)).thenReturn(dto);

        mockMvc.perform(get("/api/categories/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.name").value("文学小说"));
    }

    @Test
    @DisplayName("GET /api/categories/{id} - 分类不存在")
    void getById_notFound() throws Exception {
        when(categoryService.getById(999L))
                .thenThrow(new BusinessException("分类不存在, id=999"));

        mockMvc.perform(get("/api/categories/999"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("分类不存在, id=999"));
    }

    @Test
    @DisplayName("GET /api/categories/list - 查询所有分类")
    void listAll() throws Exception {
        CategoryDTO dto1 = new CategoryDTO();
        dto1.setId(1L);
        dto1.setName("文学小说");
        dto1.setSortOrder(1);
        dto1.setCreatedAt(NOW);
        dto1.setUpdatedAt(NOW);

        CategoryDTO dto2 = new CategoryDTO();
        dto2.setId(2L);
        dto2.setName("科技计算机");
        dto2.setSortOrder(2);
        dto2.setCreatedAt(NOW);
        dto2.setUpdatedAt(NOW);

        when(categoryService.listAll()).thenReturn(Arrays.asList(dto1, dto2));

        mockMvc.perform(get("/api/categories/list"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data.length()").value(2))
                .andExpect(jsonPath("$.data[0].name").value("文学小说"))
                .andExpect(jsonPath("$.data[1].name").value("科技计算机"));
    }

    @Test
    @DisplayName("GET /api/categories/list - 无分类数据返回空列表")
    void listAll_empty() throws Exception {
        when(categoryService.listAll()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/categories/list"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data.length()").value(0));
    }

    @Test
    @DisplayName("PUT /api/categories/{id} - 更新分类成功")
    void update_success() throws Exception {
        CategoryDTO dto = new CategoryDTO();
        dto.setId(1L);
        dto.setName("文学名著");
        dto.setDescription("更新后的描述");
        dto.setSortOrder(1);
        dto.setCreatedAt(NOW);
        dto.setUpdatedAt(NOW);

        when(categoryService.update(eq(1L), any(CategoryUpdateRequest.class))).thenReturn(dto);

        CategoryUpdateRequest request = new CategoryUpdateRequest();
        request.setName("文学名著");
        request.setDescription("更新后的描述");
        request.setSortOrder(1);

        mockMvc.perform(put("/api/categories/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data.name").value("文学名著"))
                .andExpect(jsonPath("$.data.description").value("更新后的描述"));

        verify(categoryService).update(eq(1L), any(CategoryUpdateRequest.class));
    }

    @Test
    @DisplayName("PUT /api/categories/{id} - 分类不存在")
    void update_notFound() throws Exception {
        when(categoryService.update(eq(999L), any(CategoryUpdateRequest.class)))
                .thenThrow(new BusinessException("分类不存在, id=999"));

        CategoryUpdateRequest request = new CategoryUpdateRequest();
        request.setName("新名称");

        mockMvc.perform(put("/api/categories/999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("分类不存在, id=999"));
    }

    @Test
    @DisplayName("PUT /api/categories/{id} - 名称重复")
    void update_duplicateName() throws Exception {
        when(categoryService.update(eq(1L), any(CategoryUpdateRequest.class)))
                .thenThrow(new BusinessException("分类名称已存在: 科技"));

        CategoryUpdateRequest request = new CategoryUpdateRequest();
        request.setName("科技");

        mockMvc.perform(put("/api/categories/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("分类名称已存在: 科技"));
    }

    @Test
    @DisplayName("DELETE /api/categories/{id} - 删除分类成功")
    void delete_success() throws Exception {
        doNothing().when(categoryService).delete(1L);

        mockMvc.perform(delete("/api/categories/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("success"));

        verify(categoryService).delete(1L);
    }

    @Test
    @DisplayName("DELETE /api/categories/{id} - 分类不存在")
    void delete_notFound() throws Exception {
        doThrow(new BusinessException("分类不存在, id=999"))
                .when(categoryService).delete(999L);

        mockMvc.perform(delete("/api/categories/999"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("分类不存在, id=999"));
    }

    @Test
    @DisplayName("DELETE /api/categories/{id} - 分类下有图书无法删除")
    void delete_hasBooks() throws Exception {
        doThrow(new BusinessException("该分类下存在图书，无法删除"))
                .when(categoryService).delete(1L);

        mockMvc.perform(delete("/api/categories/1"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("该分类下存在图书，无法删除"));
    }
}
