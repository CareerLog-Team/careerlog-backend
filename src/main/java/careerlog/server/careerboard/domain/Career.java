package careerlog.server.careerboard.domain;

import careerlog.server.common.entity.BaseTimeEntity;
import careerlog.server.workrecord.domain.WorkRecord;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Career extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String careerId;

    /*
    소속
     */
    private String organization;

    /*
    팀
     */
    private String team;

    /*
    직책
     */
    private String position;

    /*
    시작일
     */
    private LocalDate startDate;

    /*
    종료일
     */
    private LocalDate endDate;

    /*
    색상
     */
    private String color;

    /*
    현재 소속 여부
     */
    private Boolean isPresent;

    /*
    업무 경력
     */
    @OneToMany(mappedBy = "career")
    private List<WorkRecord> workRecords = new ArrayList<>();

    @Setter(AccessLevel.PROTECTED)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "careerBoardId")
    private CareerBoard careerBoard;

    @Builder
    public Career(String careerId, String organization, String team, String position, LocalDate startDate, LocalDate endDate, String color, Boolean isPresent) {
        this.careerId = careerId;
        this.organization = organization;
        this.team = team;
        this.position = position;
        this.startDate = startDate;
        this.endDate = endDate;
        this.color = color;
        this.isPresent = isPresent;
    }

    public void updateCareer(Career updateCareer) {
        this.organization = updateCareer.getOrganization();
        this.team = updateCareer.getTeam();
        this.position = updateCareer.getPosition();
        this.startDate = updateCareer.getStartDate();
        this.endDate = updateCareer.getEndDate();
        this.color = updateCareer.getColor();
        this.isPresent = updateCareer.getIsPresent();
    }

    public void addWorkRecord(WorkRecord workRecord) {
        workRecords.add(workRecord);
        workRecord.setCareer(this);
    }
}
