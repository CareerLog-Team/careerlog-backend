package careerlog.server.careerboard.repository;

import careerlog.server.careerboard.domain.Language;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface LanguageRepository extends JpaRepository<Language, String> {
}
