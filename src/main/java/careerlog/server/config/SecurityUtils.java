package careerlog.server.config;

import careerlog.server.common.exception.CustomException;
import careerlog.server.common.resultcode.ResultCode;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtils {

    public static String getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null) throw new CustomException(ResultCode.USER_NOT_FOUND_EXCEPTION);

        if (authentication.isAuthenticated()) {
            return authentication.getName();
        } else {
            throw new CustomException(ResultCode.UNAUTHORIZED_TOKEN);
        }
    }
}
