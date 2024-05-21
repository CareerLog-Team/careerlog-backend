package careerlog.server.careerboard.dto.request.update;


import careerlog.server.careerboard.domain.Education;
import careerlog.server.utils.LocalDateUtils;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class UpdateEducationRequestDto {
    private String educationId;
    private String educationName;
    private String degree;
    private String graduationStatus;
    private String major;
    private String startDate;
    private String endDate;

    public Education toEducationWithId() {
        return Education.builder()
                .educationId(this.getEducationId())
                .educationName(this.getEducationName())
                .degree(this.getDegree())
                .graduationStatus(this.getGraduationStatus())
                .major(this.getMajor())
                .startDate(LocalDateUtils.parse(this.getStartDate()))
                .endDate(LocalDateUtils.parse(this.getEndDate()))
                .build();
    }
}
