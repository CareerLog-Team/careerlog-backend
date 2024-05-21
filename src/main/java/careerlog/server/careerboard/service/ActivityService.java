package careerlog.server.careerboard.service;


import careerlog.server.careerboard.domain.Activity;
import careerlog.server.careerboard.dto.response.ActivityResponseDto;
import careerlog.server.careerboard.repository.ActivityRepository;
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
public class ActivityService {
    private final ActivityRepository activityRepository;

    public List<ActivityResponseDto> getActivities() {
        List<Activity> _activities = activityRepository.findAll();
        return getActivityResponseDtos(_activities);
    }

    @Transactional
    public void addActivities(List<Activity> activitiesWithoutId) {
        List<Activity> savedActivities = activityRepository.saveAll(activitiesWithoutId);

        for (Activity savedActivity : savedActivities) {
            log.info("activity => {}", savedActivity.toString());
        }
    }

    @Transactional
    public void updateActivities(List<Activity> activitiesWithId) {
        List<Activity> findActivities = activityRepository.findAll();
        Map<String, Activity> activitiesWithIdMap = getActivitiesWithIdMap(activitiesWithId);

        for (Activity findActivity : findActivities) {
            Activity updateActivity = activitiesWithIdMap.get(findActivity.getActivityId());
            findActivity.updateActivity(updateActivity);
        }
    }

    @Transactional
    public void removeActivityById(String removeActivityId) {
        Optional<Activity> _removeActivity = activityRepository.findById(removeActivityId);
        _removeActivity.ifPresent(activityRepository::delete);
    }


    private List<ActivityResponseDto> getActivityResponseDtos(List<Activity> activitiesWithoutId) {
        if (!activitiesWithoutId.isEmpty()) {
            return activitiesWithoutId.stream()
                    .map(ActivityResponseDto::new)
                    .collect(Collectors.toList());
        }
        return null;
    }

    private Map<String, Activity> getActivitiesWithIdMap(List<Activity> activitiesWithId) {
        return activitiesWithId.stream()
                .collect(Collectors.toMap(Activity::getActivityId, activity -> activity));
    }
}
