package careerlog.server.careerboard.service;


import careerlog.server.careerboard.repository.ActivityRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ActivityService {
    private final ActivityRepository activityRepository;

    @Transactional
    public void removeActivityById(String removeActivityId) {
        activityRepository
                .findById(removeActivityId)
                .ifPresent(activityRepository::delete);
    }
}
