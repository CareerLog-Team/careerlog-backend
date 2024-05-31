package careerlog.server.careerboard.domain;

import careerlog.server.common.response.exception.CustomException;
import careerlog.server.common.response.resultcode.ResultCode;
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

    public void setCareerBoard(CareerBoard careerBoard) {
        if (this.careerBoard != null) {
            // 이미 CareerBoard가 세팅되어있다는 Exception을 반환
            throw new CustomException(ResultCode.INTERNAL_SERVER_ERROR);
        }

        this.careerBoard = careerBoard;
    }
}
