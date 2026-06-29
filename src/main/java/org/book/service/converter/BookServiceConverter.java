package org.book.service.converter;

import org.book.domain.book.model.Book;
import org.book.service.dto.BookDTO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 图书服务转换器
 * <p>负责图书领域对象与 DTO 之间的转换</p>
 */
@Component
public class BookServiceConverter {

    /**
     * 将领域对象转换为 DTO
     *
     * @param book 图书领域对象
     * @return 图书 DTO
     */
    public BookDTO toDTO(Book book) {
        if (book == null) return null;
        BookDTO dto = new BookDTO();
        dto.setId(book.getId());
        dto.setTitle(book.getTitle());
        dto.setAuthor(book.getAuthor());
        dto.setIsbn(book.getIsbn());
        dto.setPublisher(book.getPublisher());
        dto.setPublishDate(book.getPublishDate());
        dto.setPrice(book.getPrice());
        dto.setStock(book.getStock());
        dto.setDescription(book.getDescription());
        dto.setCategoryId(book.getCategoryId());
        dto.setCreatedAt(book.getCreatedAt());
        dto.setUpdatedAt(book.getUpdatedAt());
        return dto;
    }

    /**
     * 将领域对象列表转换为 DTO 列表
     *
     * @param books 图书领域对象列表
     * @return 图书 DTO 列表
     */
    public List<BookDTO> toDTOList(List<Book> books) {
        return books.stream().map(this::toDTO).collect(Collectors.toList());
    }
}
