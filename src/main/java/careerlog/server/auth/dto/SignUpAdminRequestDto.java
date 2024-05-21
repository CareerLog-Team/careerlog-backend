package careerlog.server.auth.dto;

import careerlog.server.user.domain.User;
import careerlog.server.user.domain.UserRole;
import lombok.Data;

@Data
public class SignUpAdminRequestDto {

    private String email;
    private String password;

    public User toUser() {
        return User.builder()
                .email(this.getEmail())
                .password(this.getPassword())
                .userRole(UserRole.ROLE_ADMIN)
                .build();
    }
}
