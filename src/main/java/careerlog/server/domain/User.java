package careerlog.server.domain;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "USERS")
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id
    private String userId;

    public User(String userId) {
        this.userId = userId;
    }
}
