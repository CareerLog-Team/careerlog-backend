package careerlog.server.config.security;

import careerlog.server.jwt.JwtAuthenticationFilter;
import careerlog.server.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Arrays;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationEntryPoint entryPoint;
    private final AccessDeniedHandler accessDeniedHandler;


    private static final String[] AUTH_WHITELIST = {
            "/api/v0/auth/sign-in",
            "/api/v0/auth/sign-in/**",
            "/api/v0/auth/sign-up",
            "/api/v0/auth/sign-up/**"
    };

    private static final String[] AUTH_BLACKLIST = {
            "/api/v0/auth/with-draw", "/api/v0/auth/with-draw/**",
            "/api/v0/auth/sign-out", "/api/v0/auth/sign-out/**",
            "/api/v0/career-board", "/api/v0/career-board/**",
            "/api/v0/work-record", "/api/v0/work-record/**",
            "/api/v0/file", "/api/v0/file/**",
            "/api/v0/resume", "/api/v0/resume/**",
    };

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // csrf, cors
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())

                // 세션 관리 상태 없음 관리는 Jwt로 진행
                .sessionManagement(
                        sessionManagement -> sessionManagement.sessionCreationPolicy(
                                SessionCreationPolicy.STATELESS
                        )
                )
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                        (authorize) -> authorize
                        .requestMatchers("/domain/**", "/public/**", "/actuator", "/swagger-ui/**",
                                "/v3/api-docs/**", "/actuator/**", "/metrics/**").permitAll()   //Swagger는 항상 오픈
                        .requestMatchers(AUTH_WHITELIST).permitAll()                                  // 인증 없이 접근 가능한 경로
                        .requestMatchers(AUTH_BLACKLIST).authenticated()                              // 접근에 인증이 필요한 경로
                        .anyRequest().denyAll()                                                       // 그 외 모든 요청 차단
                )
                .addFilterBefore(
                        new JwtAuthenticationFilter(jwtTokenProvider),
                        UsernamePasswordAuthenticationFilter.class
                )
                .exceptionHandling(
                        (handler) ->
                                handler
                                        .authenticationEntryPoint(entryPoint)
                                        .accessDeniedHandler(accessDeniedHandler)
                );

        return http.build();
    }
}
