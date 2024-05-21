package careerlog.server.careerboard.repository;

import careerlog.server.careerboard.domain.Link;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface LinkRepository extends JpaRepository<Link, String> {
}
