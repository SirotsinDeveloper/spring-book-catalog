package by.slavik.bookcatalog.mapper;

import by.slavik.bookcatalog.model.Book;
import by.slavik.bookcatalog.model.dto.RequestBookDto;
import by.slavik.bookcatalog.model.dto.ResponseBookDto;

public interface BookMapper {
    Book toEntity(RequestBookDto requestBookDto);

    ResponseBookDto toResponseDto(Book book);
}
