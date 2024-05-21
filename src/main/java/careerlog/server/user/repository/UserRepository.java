package careerlog.server.user.repository;

import careerlog.server.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {

//    @Query("select u from User u where u.userId = :userId and u.user_status = 'ACTIVATE'")
    Optional<User> findByUserId(String userId);

//    @Query("select u from User u where u.email = :email and u.user_status = 'ACTIVATE'")
    Optional<User> findUserByEmail(String email);

}
