package careerlog.server.common.response.resultcode;


import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ResultCode {

    // 성공 케이스
    SUCCESS(HttpStatus.OK, "SUCCESS","성공!"),

    // 예외 및 에러 케이스 Domain 별 예외 정의후 활용
    // CareerBoard (CBD)
    CAREER_BOARD_NOT_FOUND_EXCEPTION(HttpStatus.NOT_FOUND, "CBD_EX_001", "이력관리 조회에 실패했습니다"),

    // Career (CAR)
    CAREER_NOT_FOUND_EXCEPTION(HttpStatus.NOT_FOUND, "CAR_EX_001", "이력 조회에 실패했습니다"),


    // Auth (ATH)

    // User (USR)
    USER_NOT_FOUND_EXCEPTION(HttpStatus.NOT_FOUND, "USR_EX_001", "사용자 조회에 실패했습니다"),
    USER_NOT_AUTHENTICATED(HttpStatus.BAD_REQUEST, "USR_EX_002", "사용자가 인증되지 않았습니다"),

    // File (FILE)

    // Resume (RES)

    // Jwt Exception (JWT)
    TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "JWT_EX_001", "토큰이 만료되었습니다"),
    INVALID_TOKEN_FORMAT(HttpStatus.UNAUTHORIZED, "JWT_EX_002", "올바르지 않은 토큰입니다."),
    UNAUTHORIZED_TOKEN(HttpStatus.UNAUTHORIZED, "JWT_EX_003", "권한이 없는 토큰입니다."),
    NO_PAYLOAD_TOKEN(HttpStatus.UNAUTHORIZED, "JWT_EX_004", "토큰 파싱이 불가능합니다."),
    TOKEN_USED_TOO_EARLY(HttpStatus.UNAUTHORIZED, "JWT_EX_005", "토큰 사용 시점이 너무 이릅니다."),
    UNSUPPORTED_TOKEN_FORMAT(HttpStatus.UNAUTHORIZED, "JWT_EX_006", "지원되지 않는 토큰 형식입니다."),
    TOKEN_COMPRESSION_FAILED(HttpStatus.UNAUTHORIZED, "JWT_EX_007", "토큰 압축 해제에 실패했습니다."),
    INVALID_TOKEN_TYPE(HttpStatus.UNAUTHORIZED, "JWT_EX_008", "필요한 타입의 토큰이 아닙니다."),


    // WorkRecord (WRD)
    WORK_RECORD_NOT_FOUND_EXCEPTION(HttpStatus.NOT_FOUND, "WRD_EX_001", "이력 기록 조회에 실패했습니다"),

    // Validation
    NOT_VALID_EXCEPTION(HttpStatus.BAD_REQUEST, "VALID_EX_001", "유효한 요청이 아닙니다."),

    // Server Error
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "ERROR", "서버오류가 발생했습니다 담당자에게 문의 바랍니다.");

    private final HttpStatus httpStatusCode;
    private final String code;
    private final String message;
}
