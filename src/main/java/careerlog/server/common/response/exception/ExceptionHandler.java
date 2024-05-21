package careerlog.server.common.response.exception;

import careerlog.server.common.dto.ResponseDto;
import careerlog.server.common.response.resultcode.ResultCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandler {
    @org.springframework.web.bind.annotation.ExceptionHandler(CustomException.class)
    protected ResponseEntity<ResponseDto<Object>> handleCustomException(CustomException e) {
        ResultCode resultCode = e.getResultCode();

        return new ResponseDto<>(
                resultCode.getCode(),
                resultCode.getMessage()
        ).toResponseEntity(resultCode.getHttpStatusCode());
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
    protected ResponseEntity<ResponseDto<Object>> handleException(Exception e) {
        ResultCode resultCode = ResultCode.INTERNAL_SERVER_ERROR;

        return new ResponseDto<>(
                resultCode.getCode(),
                e.getMessage()
        ).toResponseEntity(resultCode.getHttpStatusCode());
    }
}
