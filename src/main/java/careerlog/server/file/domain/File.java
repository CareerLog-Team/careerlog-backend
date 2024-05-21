package careerlog.server.file.domain;


import careerlog.server.common.entity.BaseTimeEntity;
import careerlog.server.user.domain.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Table(name = "files")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class File extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String fileId;

    private String originalFileName;
    private String s3Url;
    private String extension;
    private String fileSize;

    @Setter
    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user;


    @Builder
    public File(String fileId, String originalFileName, String s3Url, String extension, String fileSize, User user) {
        this.fileId = fileId;
        this.originalFileName = originalFileName;
        this.s3Url = s3Url;
        this.extension = extension;
        this.fileSize = fileSize;
        this.user = user;
    }

}
