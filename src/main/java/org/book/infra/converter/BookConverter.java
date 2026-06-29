package org.book.infra.converter;

import org.book.domain.book.model.Book;
import org.book.infra.dataobject.BookDO;
import org.springframework.stereotype.Component;

/**
 * 图书转换器
 * <p>负责图书领域对象与数据对象之间的转换</p>
 */
@Component
public class BookConverter {

    /**
     * 将领域对象转换为数据对象
     *
     * @param book 图书领域对象
     * @return 图书数据对象
     */
    public BookDO toDataObject(Book book) {
        BookDO doObj = new BookDO();
        doObj.setId(book.getId());
        doObj.setTitle(book.getTitle());
        doObj.setAuthor(book.getAuthor());
        doObj.setIsbn(book.getIsbn());
        doObj.setPublisher(book.getPublisher());
        doObj.setPublishDate(book.getPublishDate());
        doObj.setPrice(book.getPrice());
        doObj.setStock(book.getStock());
        doObj.setDescription(book.getDescription());
        doObj.setCategoryId(book.getCategoryId());
        doObj.setCreatedAt(book.getCreatedAt());
        doObj.setUpdatedAt(book.getUpdatedAt());
        return doObj;
    }

    /**
     * 将数据对象转换为领域对象
     *
     * @param doObj 图书数据对象
     * @return 图书领域对象
     */
    public Book toDomain(BookDO doObj) {
        if (doObj == null) {
            return null;
        }
        return Book.reconstruct(
                doObj.getId(),
                doObj.getTitle(),
                doObj.getAuthor(),
                doObj.getIsbn(),
                doObj.getPublisher(),
                doObj.getPublishDate(),
                doObj.getPrice(),
                doObj.getStock(),
                doObj.getDescription(),
                doObj.getCategoryId(),
                doObj.getCreatedAt(),
                doObj.getUpdatedAt()
        );
    }
}
