package careerlog.server.resume.repository;

import careerlog.server.resume.domain.ResumeStyle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResumeTypeRepository extends JpaRepository<ResumeStyle, String> {
}
