package careerlog.server.careerboard.dto.request.update;

import careerlog.server.careerboard.domain.Activity;
import careerlog.server.utils.LocalDateUtils;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class UpdateActivityRequestDto {

    private String activityId;
    private String activityName;
    private String agency;
    private String result;
    private String startDate;
    private String endDate;

    public Activity toActivityWithId() {
        return Activity.builder()
                .activityId(this.getActivityId())
                .activityName(this.getActivityName())
                .agency(this.getAgency())
                .result(this.getResult())
                .startDate(LocalDateUtils.parse(this.getStartDate()))
                .endDate(LocalDateUtils.parse(this.getEndDate()))
                .build();
    }

}
