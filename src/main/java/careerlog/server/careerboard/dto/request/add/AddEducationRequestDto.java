package careerlog.server.careerboard.dto.request.add;


import careerlog.server.careerboard.domain.Education;
import careerlog.server.utils.LocalDateUtils;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class AddEducationRequestDto {
    private String educationName;
    private String degree;
    private String graduationStatus;
    private String major;
    private String startDate;
    private String endDate;

    public Education toEducationWithoutId() {
        return Education.builder()
                .educationName(this.getEducationName())
                .degree(this.getDegree())
                .graduationStatus(this.getGraduationStatus())
                .major(this.getMajor())
                .startDate(LocalDateUtils.parse(this.getStartDate()))
                .endDate(LocalDateUtils.parse(this.getEndDate()))
                .build();
    }
}
