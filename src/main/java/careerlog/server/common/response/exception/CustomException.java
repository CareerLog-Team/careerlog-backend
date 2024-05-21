package careerlog.server.common.response.exception;


import careerlog.server.common.response.resultcode.ResultCode;
import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {
    ResultCode resultCode;

    public CustomException(ResultCode resultCode) {
        super(resultCode.getMessage());
        this.resultCode = resultCode;
    }
}
