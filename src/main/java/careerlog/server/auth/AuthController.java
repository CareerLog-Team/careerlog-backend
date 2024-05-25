package careerlog.server.auth;


import careerlog.server.auth.dto.SignInRequestDto;
import careerlog.server.auth.dto.SignUpAdminRequestDto;
import careerlog.server.auth.dto.SignUpRequestDto;
import careerlog.server.careerboard.service.CareerBoardService;
import careerlog.server.common.dto.ResponseDto;
import careerlog.server.common.response.resultcode.ResultCode;
import careerlog.server.config.security.SecurityUtils;
import careerlog.server.jwt.JwtToken;
import careerlog.server.user.domain.User;
import careerlog.server.user.service.UserService;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v0/auth")
@Tag(name = "인증 관리 컨트롤러", description = "사용자 인증 관련 API 설정을 정의합니다.")
public class AuthController {

    private final UserService userService;
    private final AuthService authService;
    private final CareerBoardService careerBoardService;

    // 1. 로그인
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "로그인 성공", content = @Content(schema = @Schema(implementation = JwtToken.class))),
    })
    @Operation(summary = "로그인 API", description = "서비스 로그인 API입니다. 성공 시, JWT 토큰을 반환합니다")
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
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "로그아웃 성공"),
    })
    @Operation(summary = "로그아웃 API", description = "서비스 로그아웃 API입니다. 아직 구현되지 않았습니다.")
    @PostMapping("/sign-out")
    public void signOut() {

    }

    // 3. 회원가입
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "회원가입 성공"),
    })
    @Operation(summary = "회원가입 API", description = "서비스 회원가입 API입니다.")
    @PostMapping("/sign-up")
    public String signUp(@RequestBody SignUpRequestDto signUpRequestDto) {
        User user = userService.addUser(signUpRequestDto.toUser());
        careerBoardService.addCareerBoard(user);

        return "ok";
    }

    @Hidden
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "어드민 계정 회원가입 성공"),
    })
    @Operation(summary = "회원가입 API", description = "어드민 계정 회원가입 API입니다. 일반적으로 활용하지 않습니다.")
    @PostMapping("/sign-up/admin")
    public String signUpAdmin(@RequestBody SignUpAdminRequestDto signUpAdminRequestDto) {
        userService.addUser(signUpAdminRequestDto.toUser());
        return "ok";
    }

    // 4. 회원탈퇴
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "회원 탈퇴 성공"),
    })
    @Operation(summary = "회원 탈퇴 API", description = "회원 탈퇴 API입니다.")
    @PostMapping("/with-draw")
    public String withDraw() {
        User user = userService.getUserById(SecurityUtils.getCurrentUserId());
        userService.withDrawUser(user);

        return "ok";
    }
}
