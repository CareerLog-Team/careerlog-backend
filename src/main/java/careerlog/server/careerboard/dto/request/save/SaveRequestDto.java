package careerlog.server.careerboard.dto.request.save;

import lombok.Data;

@Data
public class SaveRequestDto {
    private SaveCareerRequestDto saveCareerRequestDto;
    private SaveActivityRequestDto saveActivityRequestDto;
    private SaveCertificateRequestDto saveCertificateRequestDto;
    private SaveEducationRequestDto saveEducationRequestDto;
    private SaveAutoRequestDto saveAutoRequestDto;
}
