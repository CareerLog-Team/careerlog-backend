package careerlog.server.utils;

import careerlog.server.common.response.exception.CustomException;
import careerlog.server.common.response.resultcode.ResultCode;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class FileUtils {

    public static String getFileExtension(String filename) {
        int dotIndex = filename.lastIndexOf('.');
        if (dotIndex != -1 && dotIndex < filename.length() - 1) {
            return filename.substring(dotIndex + 1);
        }
        // TODO: 일단 임시로 이렇게 구성
        log.warn("getFileExtension => No File Exception / FileName : {}", filename);
        throw new CustomException(ResultCode.INTERNAL_SERVER_ERROR); // NOT_FILE_EXTENSION 에러 따로 만들기
    }


    public static String convertBytes(Long bytes) {
        int unit = 1024;
        if (bytes < unit) return bytes + " B";
        int exp = (int) (Math.log(bytes) / Math.log(unit));
        String pre = "KMGTPE".charAt(exp-1) + "B";
        return String.format("%.1f %s", bytes / Math.pow(unit, exp), pre);
    }
}
