package careerlog.server.resume.dto;


import careerlog.server.resume.domain.ResumeType;
import lombok.Builder;
import lombok.Data;

@Data
public class ResumeTypeResponseDto {
    private String resumeTypeCode;
    private String resumeTypeName;
    private String thumbNailImageUrl;

    @Builder
    public ResumeTypeResponseDto(ResumeType resumeType) {
        this.resumeTypeCode = resumeType.getResumeTypeCode();
        this.resumeTypeName = resumeType.getResumeTypeName();
        this.thumbNailImageUrl = resumeType.getThumbNailImageUrl();
    }
}
