package careerlog.server.resume.repository;

import careerlog.server.resume.domain.Resume;
import careerlog.server.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ResumeRepository extends JpaRepository<Resume, String> {

    List<Resume> findResumesByUser(User user);
}
