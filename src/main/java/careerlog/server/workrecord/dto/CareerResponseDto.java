package careerlog.server.workrecord.dto;


import careerlog.server.careerboard.domain.Career;
import careerlog.server.utils.LocalDateUtils;
import lombok.Data;
import lombok.Getter;

import java.util.List;

@Data
@Getter
public class CareerResponseDto {

    private String careerId;
    private String startDate;
    private String endDate;
    private String organization;
    private String position;
    private List<WorkRecordResponseDto> workRecordResponseDtos;

    public CareerResponseDto(Career career) {
        this.careerId = career.getCareerId();
        this.startDate = LocalDateUtils.formatDate(career.getStartDate());
        this.endDate = LocalDateUtils.formatDate(career.getEndDate());
        this.organization = career.getOrganization();
        this.position = career.getPosition();
        this.workRecordResponseDtos =
                career.getWorkRecords().stream()
                        .map(WorkRecordResponseDto::new)
                        .toList();
    }
}
