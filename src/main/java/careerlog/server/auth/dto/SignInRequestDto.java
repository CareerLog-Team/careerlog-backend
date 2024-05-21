package careerlog.server.auth.dto;


import lombok.Data;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

@Data
public class SignInRequestDto {

    private String email;
    private String password;

    public UsernamePasswordAuthenticationToken toAuthentication() {
        return new UsernamePasswordAuthenticationToken(this.getEmail(), this.getPassword());
    }

}
