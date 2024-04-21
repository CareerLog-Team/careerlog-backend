package careerlog.server.controller;


import careerlog.server.dto.SignInDto;
import careerlog.server.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<String> signIn(@RequestBody SignInDto signInDto) {
        return ResponseEntity.ok().body(userService.signInUser(signInDto));
    }
}
