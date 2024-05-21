package careerlog.server.resume.domain;


import careerlog.server.common.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ResumeItem extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String resumeItemId;

    @Enumerated(EnumType.STRING)
    private ItemType itemType;

    private Boolean isTrue;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resume_id")
    private Resume resume;

    @Builder
    public ResumeItem(String resumeItemId, ItemType itemType, Boolean isTrue, Resume resume) {
        this.resumeItemId = resumeItemId;
        this.itemType = itemType;
        this.isTrue = isTrue;
        this.resume = resume;
    }
}
