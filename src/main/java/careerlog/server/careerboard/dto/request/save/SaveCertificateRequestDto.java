package careerlog.server.careerboard.dto.request.save;


import careerlog.server.careerboard.dto.request.update.UpdateCertificateRequestDto;
import careerlog.server.careerboard.dto.request.add.AddCertificateRequestDto;
import careerlog.server.careerboard.dto.request.remove.RemoveCertificateRequestDto;
import lombok.Data;
import lombok.Getter;

import java.util.List;

@Data
@Getter
public class SaveCertificateRequestDto {

    private List<RemoveCertificateRequestDto> removeCertificateRequestDtos;
    private List<UpdateCertificateRequestDto> updateCertificateRequestDtos;
    private List<AddCertificateRequestDto> addCertificateRequestDtos;

}
