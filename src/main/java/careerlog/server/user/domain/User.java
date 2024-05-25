package careerlog.server.user.domain;


import careerlog.server.careerboard.domain.CareerBoard;
import careerlog.server.common.entity.BaseTimeEntity;
import careerlog.server.file.domain.File;
import careerlog.server.resume.domain.Resume;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseTimeEntity implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String userId;

    @Column(unique = true)
    private String email;

    private String password;

    private String phoneNumber;

    private LocalDate birthDate;

    private String gender;

    private Boolean isAgreeMarketing;

    @Enumerated(EnumType.STRING)
    private UserRole userRole;

    @Enumerated(EnumType.STRING)
    private UserStatus userStatus;

    @OneToMany(mappedBy = "user")
    private List<CareerBoard> careerBoards = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Resume> resumes = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<File> files = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(userRole.toString()));
    }

    @Override
    public String getUsername() {
        // username으로 User의 pk값 전달
        return this.getUserId();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Builder
    public User(String email, String password, String phoneNumber, LocalDate birthDate, String gender, Boolean isAgreeMarketing, UserRole userRole) {
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.birthDate = birthDate;
        this.gender = gender;
        this.isAgreeMarketing = isAgreeMarketing;
        this.userRole = userRole;
        this.userStatus = UserStatus.ACTIVATE;
    }

    public void withDraw() {
        this.userStatus = UserStatus.WITH_DRAW;
    }

    public void addCareerBoard(CareerBoard careerBoard) {
        this.careerBoards.add(careerBoard);
        careerBoard.setUser(this);
    }
}
