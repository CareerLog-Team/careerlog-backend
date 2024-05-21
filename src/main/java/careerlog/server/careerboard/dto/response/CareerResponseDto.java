package careerlog.server.careerboard.dto.response;


import careerlog.server.careerboard.domain.Career;
import careerlog.server.utils.LocalDateUtils;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class CareerResponseDto {

    private String careerId;
    private String organization;
    private String team;
    private String position;
    private String startDate;
    private String endDate;
    private String color;
    private Boolean isPresent;

    @Builder
    public CareerResponseDto(Career career) {
        this.careerId = career.getCareerId();
        this.organization = career.getOrganization();
        this.team = career.getTeam();
        this.position = career.getPosition();
        this.startDate = LocalDateUtils.formatDate(career.getStartDate());
        this.endDate = LocalDateUtils.formatDate(career.getEndDate());
        this.color = career.getColor();
        this.isPresent = career.getIsPresent();
    }
}
