package com.example.dictionary.api.error;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

/**
 * Error info.
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class ErrorInfoDto {

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    private LocalDateTime timestamp;
    private int status;
    private String error;
    private String exception;
    private String reason;
    private String message;
    private String path;

    public ErrorInfoDto(HttpStatus status, String reason, Exception exception, String path) {
        this.timestamp = LocalDateTime.now();
        this.status = status.value();
        this.error = status.getReasonPhrase();
        this.reason = reason;
        this.message = exception.getMessage();
        this.path = path;
    }
}
