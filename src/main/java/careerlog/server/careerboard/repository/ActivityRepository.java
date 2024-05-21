package careerlog.server.careerboard.repository;

import careerlog.server.careerboard.domain.Activity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActivityRepository extends JpaRepository<Activity, String> {
}
