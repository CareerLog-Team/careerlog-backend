package careerlog.server.careerboard.dto.request.save;


import careerlog.server.careerboard.dto.request.add.AddLanguageRequestDto;
import careerlog.server.careerboard.dto.request.remove.RemoveLanguageRequestDto;
import careerlog.server.careerboard.dto.request.update.UpdateLanguageRequestDto;
import lombok.Data;
import lombok.Getter;

import java.util.List;

@Data
@Getter
public class SaveLanguageRequestDto {

    private List<RemoveLanguageRequestDto> removeLanguageRequestDtos;
    private List<UpdateLanguageRequestDto> updateLanguageRequestDtos;
    private List<AddLanguageRequestDto> addLanguageRequestDtos;

}
