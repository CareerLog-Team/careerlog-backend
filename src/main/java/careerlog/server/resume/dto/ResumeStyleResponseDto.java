package careerlog.server.resume.dto;


import careerlog.server.resume.domain.ResumeStyle;
import lombok.Builder;
import lombok.Data;

@Data
public class ResumeStyleResponseDto {
    private String resumeStyleCode;
    private String resumeStyleName;
    private String thumbNailImageUrl;

    @Builder
    public ResumeStyleResponseDto(ResumeStyle resumeStyle) {
        this.resumeStyleCode = resumeStyle.getResumeStyleCode();
        this.resumeStyleName = resumeStyle.getResumeStyleName();
        this.thumbNailImageUrl = resumeStyle.getThumbNailImageUrl();
    }
}
