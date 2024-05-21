package careerlog.server.auth;


import careerlog.server.auth.dto.SignInRequestDto;
import careerlog.server.auth.dto.SignUpAdminRequestDto;
import careerlog.server.auth.dto.SignUpRequestDto;
import careerlog.server.common.dto.ResponseDto;
import careerlog.server.common.resultcode.ResultCode;
import careerlog.server.config.SecurityUtils;
import careerlog.server.jwt.JwtToken;
import careerlog.server.user.domain.User;
import careerlog.server.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v0/auth")
public class AuthController {

    private final UserService userService;
    private final AuthService authService;

    // 1. 로그인
    @PostMapping("/sign-in")
    public ResponseDto<JwtToken> signIn(
            @RequestHeader(value = "Authorization", required = false) String jwtToken,
            @RequestBody SignInRequestDto signInRequestDto
    ) {
        User user = userService.getUserByEmail(signInRequestDto.getEmail());
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(user.getUserId(), signInRequestDto.getPassword());

        JwtToken token = authService.signIn(authenticationToken);

        return new ResponseDto<>(
                ResultCode.SUCCESS.getCode(),
                token,
                ResultCode.SUCCESS.getMessage()
        );
    }

    // 2. 로그아웃
    @PostMapping("/sign-out")
    public void signOut() {

    }

    // 3. 회원가입
    @PostMapping("/sign-up")
    public ResponseDto<?> signUp(@RequestBody SignUpRequestDto signUpRequestDto) {
        userService.addUser(signUpRequestDto.toUser());

        return new ResponseDto<>(
                ResultCode.SUCCESS.getCode(),
                ResultCode.SUCCESS.getMessage()
        );
    }

    @PostMapping("/sign-up/admin")
    public ResponseDto<?> signUpAdmin(@RequestBody SignUpAdminRequestDto signUpAdminRequestDto) {
        userService.addUser(signUpAdminRequestDto.toUser());

        // 자동로그인하게 되면 로직 추가

        return new ResponseDto<>(
                ResultCode.SUCCESS.getCode(),
                ResultCode.SUCCESS.getMessage()
        );
    }

    // 4. 회원탈퇴
    @PostMapping("/with-draw")
    public ResponseDto<?> withDraw() {
        User user = userService.getUserById(SecurityUtils.getCurrentUserId());
        userService.withDrawUser(user);

        return new ResponseDto<>(
                ResultCode.SUCCESS.getCode(),
                ResultCode.SUCCESS.getMessage()
        );
    }


    // 테스트 메서드
    @GetMapping("/test/user")
    public ResponseDto<?> testUser() {
        return new ResponseDto<>(
                ResultCode.SUCCESS.getCode(),
                "일반 사용자 로그인 성공"
        );
    }

    @GetMapping("/test/admin")
    public ResponseDto<?> testAdmin() {
        return new ResponseDto<>(
                ResultCode.SUCCESS.getCode(),
                "어드민 사용자 로그인 성공"
        );
    }

}
