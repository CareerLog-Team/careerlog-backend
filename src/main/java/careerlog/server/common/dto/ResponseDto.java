package careerlog.server.common.dto;


import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

@Getter
public class ResponseDto<T> {
    private final LocalDateTime timestamp = LocalDateTime.now();
    private final String code;
    private T data;
    private final String message;


    public ResponseDto(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public ResponseDto(String code, T data, String message) {
        this.code = code;
        this.data = data;
        this.message = message;
    }

    public ResponseEntity<ResponseDto<T>> toResponseEntity(HttpStatus status) {
        return new ResponseEntity<>(
                this,
                status
        );
    }
}
