package careerlog.server.careerboard.dto.request.add;


import careerlog.server.careerboard.domain.Language;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class AddLanguageRequestDto {

    private String language;
    private String level;

    public Language toLanguageWithoutId() {
        return Language.builder()
                .language(this.getLanguage())
                .level(this.getLevel())
                .build();
    }
}
