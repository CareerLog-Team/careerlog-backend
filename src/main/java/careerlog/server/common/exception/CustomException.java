package careerlog.server.common.exception;


import careerlog.server.common.resultcode.ResultCode;
import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {
    ResultCode resultCode;

    public CustomException(ResultCode resultCode) {
        super(resultCode.getMessage());
        this.resultCode = resultCode;
    }
}
