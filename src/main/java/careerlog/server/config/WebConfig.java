package careerlog.server.config;

import careerlog.server.common.response.MyHttpMessageConverter;
import careerlog.server.intercepter.LoggerInterceptor;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final ObjectMapper objectMapper;
    private final LoggerInterceptor loggerInterceptor;

    private static final String[] EXCLUDE_PATHS = {
            "/css/**", "/images/**", "/js/**"
    };


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // loggerInterceptor 추가 +
        // Interceptor 를 수행하지 않는 PathPattern 추가
        registry.addInterceptor(loggerInterceptor)
                .excludePathPatterns(EXCLUDE_PATHS);
    }

    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        // ByteArrayHttpMessageConverter가 맨 앞에 있어야, Swagger를 랜더링 할 때, 이슈가 안생김
        converters.add(0, new ByteArrayHttpMessageConverter());
        // ResponseDto로 응답 시, String을 data 담으면 Convert 시 Parse 오류가 발생해서 별도의 MessageConverter를 만들어 추가
        converters.add(1, new MyHttpMessageConverter(objectMapper));
    }
}