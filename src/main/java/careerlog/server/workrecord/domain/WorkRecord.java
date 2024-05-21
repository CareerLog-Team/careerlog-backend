package careerlog.server.workrecord.domain;


import careerlog.server.careerboard.domain.Career;
import careerlog.server.common.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;


@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WorkRecord extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String workRecordId;

    private String title;

    // html 문서가 들어올 예정 문서를 S3에 담아서, 그 파일 경로를 전달하는 것도 방법일 듯
    private String description;

    // 별 5개 -> 100 / 별 4.5개 -> 90 / 별 4개 -> 80  10단위로 끊어서 전달
    private Integer workContributionRate;

    private Integer workAchievementRate;

    private Boolean isFinished;

    private LocalDate startDate;

    private LocalDate endDate;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "careerId")
    private Career career;

    @Builder
    public WorkRecord(String title, String description, Integer workContributionRate, Integer workAchievementRate, Boolean isFinished, LocalDate startDate, LocalDate endDate) {
        this.title = title;
        this.description = description;
        this.workContributionRate = workContributionRate;
        this.workAchievementRate = workAchievementRate;
        this.isFinished = isFinished;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public void updateWorkRecord(WorkRecord updateWorkRecord) {
        this.title = updateWorkRecord.getTitle();
        this.description = updateWorkRecord.getDescription();
        this.workContributionRate = updateWorkRecord.workContributionRate;
        this.workAchievementRate = updateWorkRecord.workAchievementRate;
        this.isFinished = updateWorkRecord.getIsFinished();
        this.startDate = updateWorkRecord.getStartDate();
        this.endDate = updateWorkRecord.getEndDate();
    }
}
