package careerlog.server.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public class LocalDateUtils {

    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static LocalDate parse(String dateString) {
        return LocalDate.parse(dateString, dateFormatter);
    }


    // LocalDate를 지정된 패턴의 String으로 변환하는 메서드
    public static String formatDate(LocalDate date) {
        return dateFormatter.format(date);
    }
}
