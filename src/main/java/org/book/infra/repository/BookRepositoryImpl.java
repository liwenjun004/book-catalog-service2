package org.book.infra.repository;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.RequiredArgsConstructor;
import org.book.domain.book.model.Book;
import org.book.domain.book.repository.BookRepository;
import org.book.infra.converter.BookConverter;
import org.book.infra.dataobject.BookDO;
import org.book.infra.mapper.BookMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 图书仓储实现类
 * <p>基于 MyBatis-Plus 实现图书的数据访问操作</p>
 */
@Repository
@RequiredArgsConstructor
public class BookRepositoryImpl implements BookRepository {

    private final BookMapper bookMapper;
    private final BookConverter bookConverter;

    @Override
    public Book findById(Long id) {
        BookDO doObj = bookMapper.selectById(id);
        return bookConverter.toDomain(doObj);
    }

    @Override
    public List<Book> findByCategoryId(Long categoryId) {
        LambdaQueryWrapper<BookDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(BookDO::getCategoryId, categoryId);
        return bookMapper.selectList(wrapper).stream()
                .map(bookConverter::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Book> findAll() {
        return bookMapper.selectList(null).stream()
                .map(bookConverter::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void save(Book book) {
        BookDO doObj = bookConverter.toDataObject(book);
        bookMapper.insert(doObj);
    }

    @Override
    public void update(Book book) {
        BookDO doObj = bookConverter.toDataObject(book);
        bookMapper.updateById(doObj);
    }

    @Override
    public void deleteById(Long id) {
        bookMapper.deleteById(id);
    }

    @Override
    public void deleteByCategoryId(Long categoryId) {
        LambdaQueryWrapper<BookDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(BookDO::getCategoryId, categoryId);
        bookMapper.delete(wrapper);
    }
}
