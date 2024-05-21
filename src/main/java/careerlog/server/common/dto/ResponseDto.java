package careerlog.server.common.dto;


import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

@Getter
public class ResponseDto<T> {
    private final LocalDateTime timestamp = LocalDateTime.now();
    private Integer code;
    private T data;
    private String message;


    public ResponseDto(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public ResponseDto(Integer code, T data, String message) {
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
