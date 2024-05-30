package careerlog.server.careerboard.dto.request.add;


import careerlog.server.careerboard.domain.Activity;
import careerlog.server.utils.LocalDateUtils;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;


@Data
@Getter
@Builder
public class AddActivityRequestDto {

    private String activityName;
    private String agency;
    private String result;
    private String startDate;
    private String endDate;

    public Activity toActivityWithoutId() {
        return Activity.builder()
                .activityName(this.getActivityName())
                .agency(this.getAgency())
                .result(this.getResult())
                .startDate(LocalDateUtils.parse(this.getStartDate()))
                .endDate(LocalDateUtils.parse(this.getEndDate()))
                .build();
    }
}
