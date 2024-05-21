package careerlog.server.careerboard.repository;

import careerlog.server.careerboard.domain.Certificate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CertificateRepository extends JpaRepository<Certificate, String> {
}
