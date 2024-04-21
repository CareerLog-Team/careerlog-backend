package careerlog.server.service;


import careerlog.server.domain.User;
import careerlog.server.dto.SignInDto;
import careerlog.server.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public String signInUser(SignInDto signInDto) {

        // Firebase에 사용자 추가
        String userId = FirebaseService.createUser(
                signInDto.getEmail(),
                signInDto.getPassword()
        );

        // 우리 DB에 사용자 추가
        User newUser = User.builder()
                .userId(userId)
                .build();

        userRepository.save(newUser);

        return userId;
    }
}
