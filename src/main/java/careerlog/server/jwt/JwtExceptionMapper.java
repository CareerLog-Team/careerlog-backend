package careerlog.server.jwt;

import careerlog.server.common.response.exception.CustomException;
import careerlog.server.common.response.resultcode.ResultCode;
import io.jsonwebtoken.*;

public class JwtExceptionMapper {
    public static CustomException toCustomException(JwtException e) {
        if (e instanceof ClaimJwtException) {
            return new CustomException(ResultCode.UNAUTHORIZED_TOKEN);
        } else if (e instanceof MalformedJwtException) {
            return new CustomException(ResultCode.INVALID_TOKEN_FORMAT);
        } else if (e instanceof UnsupportedJwtException) {
            return new CustomException(ResultCode.UNSUPPORTED_TOKEN_FORMAT);
        } else if (e instanceof CompressionException) {
            return new CustomException(ResultCode.TOKEN_COMPRESSION_FAILED);
        } else if (e instanceof RequiredTypeException) {
            return new CustomException(ResultCode.INVALID_TOKEN_TYPE);
        } else {
            // 기본적으로 JWT와 관련 없는 예외 처리 (옵션)
            return new CustomException(ResultCode.UNAUTHORIZED_TOKEN);
        }
    }
}