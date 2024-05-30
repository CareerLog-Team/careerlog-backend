package careerlog.server.careerboard.service;


import careerlog.server.careerboard.repository.CertificateRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CertificateService {

    private final CertificateRepository certificateRepository;

    @Transactional
    public void removeCertificate(String removeCertificateId) {
        certificateRepository
                .findById(removeCertificateId)
                .ifPresent(certificateRepository::delete);
    }
}
