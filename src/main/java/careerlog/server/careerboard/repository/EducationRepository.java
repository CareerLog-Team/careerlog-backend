package careerlog.server.careerboard.repository;

import careerlog.server.careerboard.domain.Education;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface EducationRepository extends JpaRepository<Education, String> {
}
