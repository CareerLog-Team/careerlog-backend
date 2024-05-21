package careerlog.server.careerboard.dto.response;


import careerlog.server.careerboard.domain.CareerBoard;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.util.List;

@Data
@Getter
public class CareerBoardResponseDto {
    private String careerBoardId;
    private Integer totalMonths;
    private Integer totalWorkRecords;
    private String myCareerGoal;
    private List<ActivityResponseDto> activityResponseDtos;
    private List<CareerResponseDto> careerResponseDtos;
    private List<CertificateResponseDto> certificateResponseDtos;
    private List<EducationResponseDto> educationResponseDtos;
    private List<LanguageResponseDto> languageResponseDtos;
    private List<LinkResponseDto> linkResponseDtos;
    private List<String> skills;

    @Builder
    public CareerBoardResponseDto(String careerBoardId, Integer totalMonths, Integer totalWorkRecords, String myCareerGoal, List<ActivityResponseDto> activityResponseDtos, List<CareerResponseDto> careerResponseDtos, List<CertificateResponseDto> certificateResponseDtos, List<EducationResponseDto> educationResponseDtos, List<LanguageResponseDto> languageResponseDtos, List<LinkResponseDto> linkResponseDtos, List<String> skills) {
        this.careerBoardId = careerBoardId;
        this.totalMonths = totalMonths;
        this.totalWorkRecords = totalWorkRecords;
        this.myCareerGoal = myCareerGoal;
        this.activityResponseDtos = activityResponseDtos;
        this.careerResponseDtos = careerResponseDtos;
        this.certificateResponseDtos = certificateResponseDtos;
        this.educationResponseDtos = educationResponseDtos;
        this.languageResponseDtos = languageResponseDtos;
        this.linkResponseDtos = linkResponseDtos;
        this.skills = skills;
    }

    public static CareerBoardResponseDto toCareerBoardResponseDto(CareerBoard careerBoard) {
        List<CareerResponseDto> careerResponseDtos = careerBoard.getCareers().stream().map(CareerResponseDto::new).toList();
        List<ActivityResponseDto> activityResponseDtos = careerBoard.getActivities().stream().map(ActivityResponseDto::new).toList();
        List<EducationResponseDto> educationResponseDtos = careerBoard.getEducations().stream().map(EducationResponseDto::new).toList();
        List<CertificateResponseDto> certificateResponseDtos = careerBoard.getCertificates().stream().map(CertificateResponseDto::new).toList();
        List<LanguageResponseDto> languageResponseDtos = careerBoard.getLanguages().stream().map(LanguageResponseDto::new).toList();
        List<LinkResponseDto> linkResponseDtos = careerBoard.getLinks().stream().map(LinkResponseDto::new).toList();
        List<String> skills = careerBoard.getSkills();

        // totalMonths, totalWorkRecords, myCareerGoal은 추후 추가

        return CareerBoardResponseDto.builder()
                .careerBoardId(careerBoard.getCareerBoardId())
                .totalMonths(null)
                .totalWorkRecords(null)
                .myCareerGoal(null)
                .careerResponseDtos(careerResponseDtos)
                .educationResponseDtos(educationResponseDtos)
                .activityResponseDtos(activityResponseDtos)
                .certificateResponseDtos(certificateResponseDtos)
                .languageResponseDtos(languageResponseDtos)
                .linkResponseDtos(linkResponseDtos)
                .skills(skills)
                .build();
    }
}