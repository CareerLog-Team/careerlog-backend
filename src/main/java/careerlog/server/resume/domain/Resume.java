package careerlog.server.resume.domain;


import careerlog.server.common.entity.BaseTimeEntity;
import careerlog.server.user.domain.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Resume extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String resumeId;

    @OneToMany(mappedBy = "resume", cascade = CascadeType.ALL)
    private List<ResumeItem> resumeItems = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY)
    private ResumeType resumeType;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user;

    @Builder
    public Resume(List<ResumeItem> resumeItems, ResumeType resumeType, User user) {
        setResumeItems(resumeItems);
        this.resumeType = resumeType;
        this.user = user;
    }

    public void setResumeItems(List<ResumeItem> resumeItems) {
        if (!resumeItems.isEmpty()) {
            this.resumeItems.addAll(resumeItems);
        }
    }
}
