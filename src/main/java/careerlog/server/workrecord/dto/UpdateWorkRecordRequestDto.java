package careerlog.server.workrecord.dto;


import careerlog.server.utils.LocalDateUtils;
import careerlog.server.workrecord.domain.WorkRecord;
import lombok.Data;

@Data
public class UpdateWorkRecordRequestDto {

    private String workRecordId;
    private String title;
    private String description;
    private Integer workContributionRate;
    private Integer workAchievementRate;
    private Boolean isFinished;
    private String startDate;
    private String endDate;

    public WorkRecord toWorkRecordWithoutId() {
        return WorkRecord.builder()
                .title(title)
                .description(description)
                .workContributionRate(workContributionRate)
                .workAchievementRate(workAchievementRate)
                .isFinished(isFinished)
                .startDate(LocalDateUtils.parse(startDate))
                .endDate(LocalDateUtils.parse(endDate))
                .build();
    }
}
