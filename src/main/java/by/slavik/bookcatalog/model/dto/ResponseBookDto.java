package by.slavik.bookcatalog.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseBookDto {
    private Long id;
    private String title;
    private String author;
    private Integer publishedYear;
}
