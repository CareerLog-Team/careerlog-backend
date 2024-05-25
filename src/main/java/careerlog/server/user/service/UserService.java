package careerlog.server.user.service;


import careerlog.server.common.response.exception.CustomException;
import careerlog.server.common.response.resultcode.ResultCode;
import careerlog.server.user.domain.User;
import careerlog.server.user.repository.UserRepository;
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

    public User getUserById(String userId) {
        return userRepository
                .findByUserId(userId)
                .orElseThrow(() -> new CustomException(ResultCode.USER_NOT_FOUND_EXCEPTION));
    }

    public User getUserByEmail(String email) {
        return userRepository
                .findUserByEmail(email)
                .orElseThrow(() -> new CustomException(ResultCode.USER_NOT_FOUND_EXCEPTION));

    }

    @Transactional
    public void withDrawUser(User user) {
        user.withDraw();
    }

    @Transactional
    public void removeUser(User user) {
        userRepository.delete(user);
    }

    @Transactional
    public User addUser(User user) {
        return userRepository.save(user);
    }


}
