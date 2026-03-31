package by.slavik.bookcatalog.model.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ResponseBookDto {
    Long id;
    String title;
    String author;
    Integer publishedYear;
}
