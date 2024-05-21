package careerlog.server.auth;


import careerlog.server.auth.dto.SignInRequestDto;
import careerlog.server.auth.dto.SignUpAdminRequestDto;
import careerlog.server.auth.dto.SignUpRequestDto;
import careerlog.server.common.dto.ResponseDto;
import careerlog.server.common.response.resultcode.ResultCode;
import careerlog.server.config.security.SecurityUtils;
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
    public JwtToken signIn(
            @RequestHeader(value = "Authorization", required = false) String jwtToken,
            @RequestBody SignInRequestDto signInRequestDto
    ) {
        User user = userService.getUserByEmail(signInRequestDto.getEmail());
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(user.getUserId(), signInRequestDto.getPassword());

        return authService.signIn(authenticationToken);
    }

    // 2. 로그아웃
    @PostMapping("/sign-out")
    public void signOut() {

    }

    // 3. 회원가입
    @PostMapping("/sign-up")
    public String signUp(@RequestBody SignUpRequestDto signUpRequestDto) {
        userService.addUser(signUpRequestDto.toUser());
        return "ok";
    }

    @PostMapping("/sign-up/admin")
    public String signUpAdmin(@RequestBody SignUpAdminRequestDto signUpAdminRequestDto) {
        userService.addUser(signUpAdminRequestDto.toUser());
        return "ok";
    }

    // 4. 회원탈퇴
    @PostMapping("/with-draw")
    public String withDraw() {
        User user = userService.getUserById(SecurityUtils.getCurrentUserId());
        userService.withDrawUser(user);

        return "ok";
    }
}
