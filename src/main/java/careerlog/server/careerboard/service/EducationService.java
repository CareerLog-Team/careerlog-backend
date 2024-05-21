package careerlog.server.careerboard.service;


import careerlog.server.careerboard.domain.Education;
import careerlog.server.careerboard.dto.response.EducationResponseDto;
import careerlog.server.careerboard.repository.EducationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class EducationService {
    private final EducationRepository educationRepository;

    public List<EducationResponseDto> getEducations() {
        List<Education> educations = educationRepository.findAll();
        return toEducationResponseDtos(educations);
    }

    @Transactional
    public void addEducations(List<Education> educationsWithoutId) {
        List<Education> savedEducations = educationRepository.saveAll(educationsWithoutId);

        for (Education savedEducation : savedEducations) {
            log.info("savedEducation => {}", savedEducation.toString());
        }
    }

    @Transactional
    public void updateEducations(List<Education> educationsWithId) {
        List<Education> findEducations = educationRepository.findAll();
        Map<String, Education> educationWithIdMap = getEducationWithIdMap(educationsWithId);

        for (Education findEducation : findEducations) {
            Education updateEducation = educationWithIdMap.get(findEducation.getEducationId());
            findEducation.updateEducation(updateEducation);
        }
    }

    @Transactional
    public void removeEducation(String removeEducationId) {
        Optional<Education> _removeEducation = educationRepository.findById(removeEducationId);
        _removeEducation.ifPresent(educationRepository::delete);
    }

    private Map<String, Education> getEducationWithIdMap(List<Education> educationsWithId) {
        return educationsWithId.stream()
                .collect(Collectors.toMap(Education::getEducationId, education -> education));
    }

    private List<EducationResponseDto> toEducationResponseDtos(List<Education> educations) {
         return educations.stream()
                .map(EducationResponseDto::new)
                .toList();
    }

}
