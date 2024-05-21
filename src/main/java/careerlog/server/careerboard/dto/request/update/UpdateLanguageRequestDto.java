package careerlog.server.careerboard.dto.request.update;


import careerlog.server.careerboard.domain.Language;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class UpdateLanguageRequestDto {

    private String languageId;
    private String language;
    private String level;

    public Language toLanguageWithId() {
        return Language.builder()
                .languageId(this.getLanguageId())
                .language(this.getLanguage())
                .level(this.getLevel())
                .build();
    }
}
