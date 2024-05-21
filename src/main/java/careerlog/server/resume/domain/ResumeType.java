package careerlog.server.resume.domain;


import careerlog.server.common.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "resume_master")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ResumeType extends BaseTimeEntity {

    /*
    ResumeTypeCode Convention -> RESUME_{Region}_{Number} => RESUME_KR_01 / RESUME_EN_01
     */
    @Id @Column(unique = true)
    private String resumeTypeCode;
    private String resumeTypeName;
    // 해외 이력서 추가를 염두 (최초에는 무조건 KR)
    private String resumeTypeRegion;
    private String thumbNailImageUrl;

    @Builder
    public ResumeType(String resumeTypeCode, String resumeTypeName) {
        this.resumeTypeCode = resumeTypeCode;
        this.resumeTypeName = resumeTypeName;
        this.resumeTypeRegion = "KR";
        this.thumbNailImageUrl = "hello world";
    }
}
