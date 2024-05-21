package careerlog.server.careerboard.dto.request.update;


import careerlog.server.careerboard.domain.Certificate;
import careerlog.server.utils.LocalDateUtils;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class UpdateCertificateRequestDto {
    private String certificateId;
    private String certificateName;
    private String officialCertificateId;
    private String score;
    private String acquisitionDt;
    private String agency;

    public Certificate toCertificateWithId() {
        return Certificate.builder()
                .certificateId(this.getCertificateId())
                .certificateName(this.getCertificateName())
                .officialCertificateId(this.getOfficialCertificateId())
                .score(this.getScore())
                .acquisitionDt(LocalDateUtils.parse(this.getAcquisitionDt()))
                .agency(this.getAgency())
                .build();
    }
}
