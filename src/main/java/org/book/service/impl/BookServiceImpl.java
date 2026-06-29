package org.book.service.impl;

import lombok.RequiredArgsConstructor;
import org.book.common.BusinessException;
import org.book.domain.book.model.Book;
import org.book.domain.book.repository.BookRepository;
import org.book.domain.category.repository.CategoryRepository;
import org.book.service.BookService;
import org.book.service.converter.BookServiceConverter;
import org.book.service.dto.BookCreateRequest;
import org.book.service.dto.BookDTO;
import org.book.service.dto.BookUpdateRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 图书服务实现类
 * <p>提供图书的创建、查询、修改、删除等业务操作</p>
 */
@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final CategoryRepository categoryRepository;
    private final BookServiceConverter converter;

    @Override
    @Transactional
    public BookDTO create(BookCreateRequest request) {
        if (categoryRepository.findById(request.getCategoryId()) == null) {
            throw new BusinessException("分类不存在, categoryId=" + request.getCategoryId());
        }
        Book book = Book.create(
                request.getTitle(),
                request.getAuthor(),
                request.getIsbn(),
                request.getPublisher(),
                request.getPublishDate(),
                request.getPrice(),
                request.getStock(),
                request.getDescription(),
                request.getCategoryId()
        );
        bookRepository.save(book);
        return converter.toDTO(book);
    }

    @Override
    public BookDTO getById(Long id) {
        Book book = bookRepository.findById(id);
        if (book == null) {
            throw new BusinessException("图书不存在, id=" + id);
        }
        return converter.toDTO(book);
    }

    @Override
    public List<BookDTO> listAll() {
        return converter.toDTOList(bookRepository.findAll());
    }

    @Override
    public List<BookDTO> listByCategoryId(Long categoryId) {
        if (categoryRepository.findById(categoryId) == null) {
            throw new BusinessException("分类不存在, categoryId=" + categoryId);
        }
        return converter.toDTOList(bookRepository.findByCategoryId(categoryId));
    }

    @Override
    @Transactional
    public BookDTO update(Long id, BookUpdateRequest request) {
        Book book = bookRepository.findById(id);
        if (book == null) {
            throw new BusinessException("图书不存在, id=" + id);
        }
        if (categoryRepository.findById(request.getCategoryId()) == null) {
            throw new BusinessException("分类不存在, categoryId=" + request.getCategoryId());
        }
        book.update(
                request.getTitle(),
                request.getAuthor(),
                request.getIsbn(),
                request.getPublisher(),
                request.getPublishDate(),
                request.getPrice(),
                request.getStock(),
                request.getDescription(),
                request.getCategoryId()
        );
        bookRepository.update(book);
        return converter.toDTO(book);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Book book = bookRepository.findById(id);
        if (book == null) {
            throw new BusinessException("图书不存在, id=" + id);
        }
        bookRepository.deleteById(id);
    }
}
