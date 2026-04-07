package by.slavik.bookcatalog.mapper.impl;

import by.slavik.bookcatalog.mapper.BookMapper;
import by.slavik.bookcatalog.model.Book;
import by.slavik.bookcatalog.model.dto.RequestBookDto;
import by.slavik.bookcatalog.model.dto.ResponseBookDto;
import org.springframework.stereotype.Component;

@Component
public class BookMapperImpl implements BookMapper {

    @Override
    public Book toEntity(RequestBookDto requestBookDto) {
        if (requestBookDto == null) {
            throw new IllegalArgumentException("RequestBookDto не может быть null");
        }
        return Book.builder()
                .title(requestBookDto.getTitle())
                .author(requestBookDto.getAuthor())
                .publishedYear(requestBookDto.getPublishedYear())
                .build();
    }

    @Override
    public ResponseBookDto toResponseDto(Book book) {
        if (book == null) {
            throw new IllegalArgumentException("Book не может быть null");
        }

        return ResponseBookDto.builder()
                .id(book.getId())
                .title(book.getTitle())
                .author(book.getAuthor())
                .publishedYear(book.getPublishedYear())
                .build();
    }
}
