package careerlog.server.file.repository;

import careerlog.server.file.domain.File;
import careerlog.server.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FileRepository extends JpaRepository<File, String> {

    List<File> findFilesByUser(User user);
}
