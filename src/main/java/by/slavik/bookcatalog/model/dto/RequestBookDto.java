package by.slavik.bookcatalog.model.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RequestBookDto {

    @NotBlank(message = "Title не может быть пустым")
    private String title;

    @NotBlank(message = "Author не можеть быть пустым")
    private String author;

    @Min(value = 1500, message = "Год не может быть меньше 1500")
    @Max(value = 2026, message = "Год не мможет быть больше текущего")
    private Integer publishedYear;
}
