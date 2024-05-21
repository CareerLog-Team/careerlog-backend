package careerlog.server.careerboard.domain;


import careerlog.server.common.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Education extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String educationId;

    /*
    학교명
     */
    private String educationName;

    /*
    학력
     */
    private String degree;

    /*
    졸업 여부
     */
    private String graduationStatus;

    /*
    학과
     */
    private String major;

    /*
    시작일
     */
    private LocalDate startDate;

    /*
    종료일
     */
    private LocalDate endDate;

    @Setter(AccessLevel.PROTECTED)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "careerBoardId")
    private CareerBoard careerBoard;

    @Builder
    public Education(String educationId, String educationName, String degree, String graduationStatus, String major, LocalDate startDate, LocalDate endDate) {
        this.educationId = educationId;
        this.educationName = educationName;
        this.degree = degree;
        this.graduationStatus = graduationStatus;
        this.major = major;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public void updateEducation(Education updateEducation) {
        this.educationName = updateEducation.getEducationName();
        this.degree = updateEducation.getDegree();
        this.graduationStatus = updateEducation.getGraduationStatus();
        this.major = updateEducation.getMajor();
        this.startDate = updateEducation.getStartDate();
        this.endDate = updateEducation.getEndDate();
    }
}
