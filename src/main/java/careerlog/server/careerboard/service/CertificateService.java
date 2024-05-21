package careerlog.server.careerboard.service;


import careerlog.server.careerboard.domain.Certificate;
import careerlog.server.careerboard.dto.response.CertificateResponseDto;
import careerlog.server.careerboard.repository.CertificateRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CertificateService {

    private final CertificateRepository certificateRepository;

    public List<CertificateResponseDto> getCertificates() {
        List<Certificate> certificates = certificateRepository.findAll();
        return toCertificateResponseDtos(certificates);
    }

    @Transactional
    public void addCertificates(List<Certificate> certificatesWithoutId) {
        List<Certificate> savedCertificates = certificateRepository.saveAll(certificatesWithoutId);

        for (Certificate savedCertificate : savedCertificates) {
            log.info("savedCertificate => {}", savedCertificate.toString());
        }
    }

    @Transactional
    public void updateCertificates(List<Certificate> certificatesWithId) {
        List<Certificate> findCertificates = certificateRepository.findAll();
        Map<String, Certificate> certificateWithIdMap = getCertificateWithIdMap(certificatesWithId);

        for (Certificate findCertificate : findCertificates) {
            Certificate updateCertificate = certificateWithIdMap.get(findCertificate.getCertificateId());
            findCertificate.updateCertificate(updateCertificate);
        }
    }

    @Transactional
    public void removeCertificate(String removeCertificateId) {
        Optional<Certificate> _removeCertificate = certificateRepository.findById(removeCertificateId);
        _removeCertificate.ifPresent(certificateRepository::delete);
    }


    private List<CertificateResponseDto> toCertificateResponseDtos(List<Certificate> certificates) {
    return certificates.stream()
            .map(CertificateResponseDto::new)
            .collect(Collectors.toList());
    }

    private Map<String, Certificate> getCertificateWithIdMap(List<Certificate> certificatesWithId) {
        return certificatesWithId.stream()
                .collect(Collectors.toMap(Certificate::getCertificateId, certificate -> certificate));
    }
}
