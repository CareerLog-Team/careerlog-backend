package careerlog.server.careerboard.service;


import careerlog.server.careerboard.repository.EducationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class EducationService {
    private final EducationRepository educationRepository;

    @Transactional
    public void removeEducation(String removeEducationId) {
        educationRepository
                .findById(removeEducationId)
                .ifPresent(educationRepository::delete);
    }
}
