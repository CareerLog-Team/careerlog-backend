package careerlog.server.user.service;

import careerlog.server.common.response.exception.CustomException;
import careerlog.server.common.response.resultcode.ResultCode;
import careerlog.server.user.domain.User;
import careerlog.server.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;



@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // userdetails.User.username ==> domain.User.userId

        return userRepository.findByUserId(username)
                .map(this::getUserDetails)
                .orElseThrow(() -> new CustomException(ResultCode.USER_NOT_FOUND_EXCEPTION));
    }

    private UserDetails getUserDetails(User user) {
        return new org.springframework.security.core.userdetails.User(
                user.getUserId(),
                encodePassword(user.getPassword()),
                user.getAuthorities()
        );
    }

    private String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }

}
