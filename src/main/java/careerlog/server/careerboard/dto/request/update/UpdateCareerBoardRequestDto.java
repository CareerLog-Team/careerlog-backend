package careerlog.server.careerboard.dto.request.update;

import lombok.Data;
import lombok.Getter;

import java.util.List;

@Data
@Getter
public class UpdateCareerBoardRequestDto {

    private List<UpdateActivityRequestDto> updateActivityRequestDtos;
    private List<UpdateCareerRequestDto> updateCareerRequestDtos;
    private List<UpdateCertificateRequestDto> updateCertificateRequestDtos;
    private List<UpdateEducationRequestDto> updateEducationRequestDtos;
    private List<UpdateLanguageRequestDto> updateLanguageRequestDtos;
    private List<UpdateLinkRequestDto> updateLinkRequestDtos;

}

