package careerlog.server.resume.repository;

import careerlog.server.resume.domain.ResumeType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResumeTypeRepository extends JpaRepository<ResumeType, String> {
}
