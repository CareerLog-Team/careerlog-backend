package careerlog.server.careerboard.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Language {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String languageId;

    /*
    언어
     */
    private String language;

    /*
    수준
     */
    private String level;

    @Setter(AccessLevel.PROTECTED)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "careerBoardId")
    private CareerBoard careerBoard;

    @Builder
    public Language(String languageId, String language, String level) {
        this.languageId = languageId;
        this.language = language;
        this.level = level;
    }

    public void updateLanguage(Language updateLanguage) {
        this.language = updateLanguage.getLanguage();
        this.level = updateLanguage.getLevel();
    }
}
