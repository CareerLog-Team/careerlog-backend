package careerlog.server.auth;


import careerlog.server.jwt.JwtToken;
import careerlog.server.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider tokenProvider;

    public JwtToken signIn(UsernamePasswordAuthenticationToken authenticationToken) {
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        log.info("로그인에 성공하였습니다.");
        log.debug("""
                이메일 : {}
                비밀번호 : {}
                권한 : {}""",
                authentication.getCredentials(),
                authentication.getPrincipal(),
                authentication.getAuthorities()
        );

        return tokenProvider.generateToken(authentication);

        // refreshToken 저장 로직 나중에 저장
    }

}
