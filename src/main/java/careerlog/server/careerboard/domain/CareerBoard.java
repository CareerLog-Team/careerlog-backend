package careerlog.server.careerboard.domain;


import careerlog.server.common.entity.BaseTimeEntity;
import careerlog.server.user.domain.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CareerBoard extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String careerBoardId;

    /*
    업무 경력
     */
    @OneToMany(mappedBy = "careerBoard")
    private List<Career> careers = new ArrayList<>();

    /*
    학력
     */
    @OneToMany(mappedBy = "careerBoard")
    private List<Education> educations = new ArrayList<>();

    /*
    활동 & 경험
     */
    @OneToMany(mappedBy = "careerBoard")
    private List<Activity> activities = new ArrayList<>();

    /*
    자격
     */
    @OneToMany(mappedBy = "careerBoard")
    private List<Certificate> certificates = new ArrayList<>();

    /*
    외국어
     */
    @OneToMany(mappedBy = "careerBoard")
    private List<Language> languages = new ArrayList<>();

    /*
    링크
     */
    @OneToMany(mappedBy = "careerBoard")
    private List<Link> links = new ArrayList<>();

    /*
    스킬
    */
    private String skills;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user;


    public static CareerBoard createCareerBoard(String skills, User user) {
        return CareerBoard.builder()
                .skills(skills)
                .user(user)
                .build();
    }

    @Builder
    public CareerBoard(String skills, User user) {
        this.skills = skills;
        this.user = user;
    }

    // 추가 로직
    public void addActivity(Activity addActivity) {
        activities.add(addActivity);
        addActivity.setCareerBoard(this);
    }

    public void addEducation(Education addEducation) {
        educations.add(addEducation);
        addEducation.setCareerBoard(this);
    }

    public void addCertificate(Certificate addCertificate) {
        certificates.add(addCertificate);
        addCertificate.setCareerBoard(this);
    }

    public void addCareer(Career addCareer) {
        careers.add(addCareer);
        addCareer.setCareerBoard(this);
    }

    public void addLanguage(Language addLanguage) {
        languages.add(addLanguage);
        addLanguage.setCareerBoard(this);
    }

    public void addLink(Link addLink) {
        links.add(addLink);
        addLink.setCareerBoard(this);
    }

    public List<String> getSkills() {
        return splitSkills(this.skills);
    }

    public void setSkills(List<String> skills) {
        this.skills = joinSkills(skills);
    }

    private String joinSkills(List<String> skills) {
        return String.join(",", skills);
    }

    private List<String> splitSkills(String joinedSkills) {
        if (!joinedSkills.isEmpty()) {
            return List.of(joinedSkills.split(","));
        }
        return null;
    }
}
