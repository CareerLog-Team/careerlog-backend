package careerlog.server.dto;


import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SignInDto {
    private String email;
    private String password;

    public SignInDto(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
