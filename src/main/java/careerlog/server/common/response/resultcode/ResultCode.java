package careerlog.server.common.response.resultcode;


import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ResultCode {

    // 성공 케이스
    SUCCESS(HttpStatus.OK, 200,"성공!"),

    // 예외 및 에러 케이스 Domain 별 예외 정의후 활용
    // 1000 ~ 1999 CareerBoard
    CAREER_BOARD_NOT_FOUND_EXCEPTION(HttpStatus.NOT_FOUND, 1000, "이력관리 조회에 실패했습니다"),

    // 2000 ~ 2999 User
    USER_NOT_FOUND_EXCEPTION(HttpStatus.NOT_FOUND, 2000, "사용자 조회에 실패했습니다"),
    USER_NOT_AUTHENTICATED(HttpStatus.BAD_REQUEST, 2001, "사용자가 인증되지 않았습니다"),

    // 3000 ~ 3999 Career
    CAREER_NOT_FOUND_EXCEPTION(HttpStatus.NOT_FOUND, 3000, "이력 조회에 실패했습니다"),

    // 4000 ~ 4999 Empty
    // 5000 ~ 5999 Empty
    // 6000 ~ 6999 Empty

    // 7000 ~ 7999 Jwt Exception
    TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, 7000, "토큰이 만료되었습니다"),
    INVALID_TOKEN_FORMAT(HttpStatus.UNAUTHORIZED, 7001, "올바르지 않은 토큰입니다."),
    UNAUTHORIZED_TOKEN(HttpStatus.UNAUTHORIZED, 7002, "권한이 없는 토큰입니다."),
    NO_PAYLOAD_TOKEN(HttpStatus.UNAUTHORIZED, 7003, "토큰 파싱이 불가능합니다."),
    TOKEN_USED_TOO_EARLY(HttpStatus.UNAUTHORIZED, 7004, "토큰 사용 시점이 너무 이릅니다."),
    UNSUPPORTED_TOKEN_FORMAT(HttpStatus.UNAUTHORIZED, 7005, "지원되지 않는 토큰 형식입니다."),
    TOKEN_COMPRESSION_FAILED(HttpStatus.UNAUTHORIZED, 7006, "토큰 압축 해제에 실패했습니다."),
    INVALID_TOKEN_TYPE(HttpStatus.UNAUTHORIZED, 7007, "필요한 타입의 토큰이 아닙니다."),


    // 8000 ~ 8999 WorkRecord
    WORK_RECORD_NOT_FOUND_EXCEPTION(HttpStatus.NOT_FOUND, 8000, "이력 기록 조회에 실패했습니다"),

    // 9000 ~ 9999 Server Error
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, 9999, "서버오류가 발생했습니다 담당자에게 문의 바랍니다.");

    private final HttpStatus httpStatusCode;
    private final Integer code;
    private final String message;
}
