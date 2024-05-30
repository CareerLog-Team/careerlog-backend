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

    public boolean checkLanguageIsNull() {
        if (saveLanguageRequestDto.getRemoveLanguageRequestDtos().isEmpty()) return true;
        if (saveLanguageRequestDto.getUpdateLanguageRequestDtos().isEmpty()) return true;
        if (saveLanguageRequestDto.getAddLanguageRequestDtos().isEmpty())    return true;

        return false;
    }

    public boolean checkLinkIsNull() {
        if (saveLinkRequestDto.getRemoveLinkRequestDtos().isEmpty()) return true;
        if (saveLinkRequestDto.getUpdateLinkRequestDtos().isEmpty()) return true;
        if (saveLinkRequestDto.getAddLinkRequestDtos().isEmpty())    return true;

        return false;
    }

    public boolean checkSkillIsNull() {
        return skills.isEmpty();
    }
}
