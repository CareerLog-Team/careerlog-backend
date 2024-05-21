package careerlog.server.careerboard.dto.response;


import careerlog.server.careerboard.domain.Language;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class LanguageResponseDto {

    private String languageId;
    private String language;
    private String level;

    public LanguageResponseDto(Language language) {
        this.languageId = language.getLanguageId();
        this.language = language.getLanguage();
        this.level = language.getLevel();
    }
}
