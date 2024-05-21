package careerlog.server.careerboard.dto.request.add;


import careerlog.server.careerboard.domain.Certificate;
import careerlog.server.utils.LocalDateUtils;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class AddCertificateRequestDto {
    private String certificateName;
    private String officialCertificateId;
    private String score;
    private String acquisitionDt;
    private String agency;

    public Certificate toCertificateWithoutId() {
        return Certificate.builder()
                .certificateName(this.certificateName)
                .officialCertificateId(this.officialCertificateId)
                .score(this.score)
                .acquisitionDt(LocalDateUtils.parse(this.acquisitionDt))
                .agency(this.agency)
                .build();
    }
}
