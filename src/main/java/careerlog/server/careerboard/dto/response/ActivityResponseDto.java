package careerlog.server.careerboard.dto.response;


import careerlog.server.careerboard.domain.Activity;
import careerlog.server.utils.LocalDateUtils;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ActivityResponseDto {
    private String activityId;
    private String activityName;
    private String agency;
    private String result;
    private String startDate;
    private String endDate;

    public ActivityResponseDto(Activity activity) {
        this.activityId = activity.getActivityId();
        this.activityName = activity.getActivityName();
        this.agency = activity.getAgency();
        this.result = activity.getResult();
        this.startDate = LocalDateUtils.formatDate(activity.getStartDate());
        this.endDate = LocalDateUtils.formatDate(activity.getEndDate());
    }
}
