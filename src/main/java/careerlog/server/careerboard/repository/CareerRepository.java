package careerlog.server.careerboard.repository;


import careerlog.server.careerboard.domain.Career;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CareerRepository extends JpaRepository<Career, String> {
}
