package careerlog.server.careerboard.domain;


import careerlog.server.common.entity.BaseTimeEntity;
import careerlog.server.common.response.exception.CustomException;
import careerlog.server.common.response.resultcode.ResultCode;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Certificate extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String certificateId;

    /*
    자격명
     */
    private String certificateName;

    /*
    발급 번호
     */
    private String officialCertificateId;

    /*
    점수/급수
     */
    private String score;

    /*
    취득일
     */
    private LocalDate acquisitionDt;

    /*
    발급 기관
     */
    private String agency;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "careerBoardId")
    private CareerBoard careerBoard;

    @Builder
    public Certificate(String certificateId, String certificateName, String officialCertificateId, String score, LocalDate acquisitionDt, String agency) {
        this.certificateId = certificateId;
        this.certificateName = certificateName;
        this.officialCertificateId = officialCertificateId;
        this.score = score;
        this.acquisitionDt = acquisitionDt;
        this.agency = agency;
    }

    public void updateCertificate(Certificate updateCertificate) {
        this.certificateName = updateCertificate.getCertificateName();
        this.officialCertificateId = updateCertificate.getOfficialCertificateId();
        this.score = updateCertificate.getScore();
        this.acquisitionDt = updateCertificate.getAcquisitionDt();
        this.agency = updateCertificate.getAgency();
    }

    public void setCareerBoard(CareerBoard careerBoard) {
        if (this.careerBoard != null) {
            // 이미 CareerBoard가 세팅되어있다는 Exception을 반환
            throw new CustomException(ResultCode.INTERNAL_SERVER_ERROR);
        }

        this.careerBoard = careerBoard;
    }
}
