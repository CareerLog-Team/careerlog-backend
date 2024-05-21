package careerlog.server.careerboard.dto.request.update;


import careerlog.server.careerboard.domain.Career;
import careerlog.server.utils.LocalDateUtils;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class UpdateCareerRequestDto {
    private String careerId;
    private String organization;
    private String team;
    private String position;
    private String startDate;
    private String endDate;
    private String color;
    private Boolean isPresent;

    public Career toCareerEntityWithId() {
        return Career.builder()
                .careerId(this.getCareerId())
                .organization(this.getOrganization())
                .team(this.getTeam())
                .position(this.getPosition())
                .startDate(LocalDateUtils.parse(this.getStartDate()))
                .endDate(LocalDateUtils.parse(this.getEndDate()))
                .color(this.getColor())
                .isPresent(this.getIsPresent())
                .build();
    }
}
