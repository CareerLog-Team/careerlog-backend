package careerlog.server.careerboard.domain;


import careerlog.server.common.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Activity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String activityId;

    /*
    활동명
     */
    private String activityName;

    /*
    기관명
     */
    private String agency;

    /*
    결과
     */
    private String result;

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
    public Activity(String activityId, String activityName, String agency, String result, LocalDate startDate, LocalDate endDate) {
        this.activityId = activityId;
        this.activityName = activityName;
        this.agency = agency;
        this.result = result;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public void updateActivity(Activity updatedActivity) {
        this.activityName = updatedActivity.getActivityName();
        this.agency = updatedActivity.getAgency();
        this.result = updatedActivity.getResult();
        this.startDate = updatedActivity.getStartDate();
        this.endDate = updatedActivity.getEndDate();
    }
}
