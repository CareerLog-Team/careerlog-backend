package careerlog.server.workrecord.dto;


import careerlog.server.utils.LocalDateUtils;
import careerlog.server.workrecord.domain.WorkRecord;
import lombok.Data;
import lombok.Getter;


@Data
@Getter
public class WorkRecordResponseDto {

    private String workRecordId;
    private String title;
    private String description;
    private Integer workContributionRate;
    private Integer workAchievementRate;
    private Boolean isFinished;
    private String startDate;
    private String endDate;

    public WorkRecordResponseDto(WorkRecord workRecord) {
        this.workRecordId = workRecord.getWorkRecordId();
        this.title = workRecord.getTitle();
        this.description = workRecord.getDescription();
        this.workContributionRate = workRecord.getWorkContributionRate();
        this.workAchievementRate = workRecord.getWorkAchievementRate();
        this.isFinished = workRecord.getIsFinished();
        this.startDate = LocalDateUtils.formatDate(workRecord.getStartDate());
        this.endDate = LocalDateUtils.formatDate(workRecord.getEndDate());
    }
}
