package careerlog.server.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private static final String[] AUTH_WHITELIST = {
            "/api/v1/**", "/swagger-ui/**",
            "/api/v1/sign-in/**", "/api/v1/sign-up/**"
    };

    private static final String[] AUTH_BLACKLIST = {
            "/api/v1/users", "/api/v1/users/**",
            "/api/v1/demo", "/api/v1/demo/**"
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // csrf, cors
        http.csrf(AbstractHttpConfigurer::disable);
        http.cors(Customizer.withDefaults());

        // 세션 관리 상태 없음 관리는 Jwt로 진행
        http.sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(
                SessionCreationPolicy.STATELESS
        ));

        // FormLogin, httpBasic 비활성화
        http.formLogin(AbstractHttpConfigurer::disable);
        http.httpBasic(AbstractHttpConfigurer::disable);


        http.authorizeHttpRequests((authorize) -> authorize
                .requestMatchers(AUTH_WHITELIST).permitAll()       // 인증 없이 접근 가능한 경로
                .requestMatchers(AUTH_BLACKLIST).authenticated()   // 접근에 인증이 필요한 경로
        );

        return http.build();
    }
}
