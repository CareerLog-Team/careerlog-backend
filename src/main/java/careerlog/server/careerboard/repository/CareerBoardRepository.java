package careerlog.server.careerboard.repository;


import careerlog.server.careerboard.domain.CareerBoard;
import careerlog.server.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CareerBoardRepository extends JpaRepository<CareerBoard, String> {

    Optional<CareerBoard> findCareerBoardByUser(User user);
}
