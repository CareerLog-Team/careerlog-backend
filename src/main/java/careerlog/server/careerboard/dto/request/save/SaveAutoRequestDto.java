package careerlog.server.careerboard.dto.request.save;


import lombok.Data;
import lombok.Getter;

import java.util.List;

@Data
@Getter
public class SaveAutoRequestDto {
    private SaveLanguageRequestDto saveLanguageRequestDto;
    private SaveLinkRequestDto saveLinkRequestDto;
    private List<String> skills;
}
