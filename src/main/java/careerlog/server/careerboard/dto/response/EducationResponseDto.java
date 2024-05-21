package careerlog.server.careerboard.dto.response;


import careerlog.server.careerboard.domain.Education;
import careerlog.server.utils.LocalDateUtils;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class EducationResponseDto {
    private String educationId;
    private String educationName;
    private String degree;
    private String graduationStatus;
    private String major;
    private String startDate;
    private String endDate;

    public EducationResponseDto(Education education) {
        this.educationId = education.getEducationId();
        this.educationName = education.getEducationName();
        this.degree = education.getDegree();
        this.graduationStatus = education.getGraduationStatus();
        this.major = education.getMajor();
        this.startDate = LocalDateUtils.formatDate(education.getStartDate());
        this.endDate = LocalDateUtils.formatDate(education.getEndDate());
    }
}
