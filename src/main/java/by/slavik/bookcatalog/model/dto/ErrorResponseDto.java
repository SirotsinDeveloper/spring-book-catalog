package by.slavik.bookcatalog.model.dto;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;

@Value
@Builder
public class ErrorResponseDto {
    LocalDateTime timestamp;
    int status;
    String error;
    String message;
    String path;
}