package careerlog.server.careerboard.dto.response;


import careerlog.server.careerboard.domain.Certificate;
import careerlog.server.utils.LocalDateUtils;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class CertificateResponseDto {

    private String certificateId;
    private String certificateName;
    private String officialCertificateId;
    private String score;
    private String acquisitionDt;
    private String agency;

    public CertificateResponseDto(Certificate certificate) {
        this.certificateId = certificate.getCertificateId();
        this.certificateName = certificate.getCertificateName();
        this.officialCertificateId = certificate.getOfficialCertificateId();
        this.score = certificate.getScore();
        this.acquisitionDt = LocalDateUtils.formatDate(certificate.getAcquisitionDt());
        this.agency = certificate.getAgency();
    }
}
