package careerlog.server.common.response.exception;


import careerlog.server.common.response.resultcode.ResultCode;
import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {
    ResultCode resultCode;

    // 미리 세팅된 ResultCode를 사용할 경우 활용
    public CustomException(ResultCode resultCode) {
        super(resultCode.getMessage());
        this.resultCode = resultCode;
    }

    // 별도의 Exception Message가 있는 경우 활용
    public CustomException(ResultCode resultCode, String message) {
        super(message);
        this.resultCode = resultCode;
    }
}
