package careerlog.server.careerboard.legacy.controller;


import careerlog.server.common.dto.ResponseDto;
import careerlog.server.common.response.resultcode.ResultCode;
import careerlog.server.careerboard.domain.Activity;
import careerlog.server.careerboard.dto.request.add.AddActivityRequestDto;
import careerlog.server.careerboard.dto.request.remove.RemoveActivityRequestDto;
import careerlog.server.careerboard.dto.request.update.UpdateActivityRequestDto;
import careerlog.server.careerboard.dto.response.ActivityResponseDto;
import careerlog.server.careerboard.service.ActivityService;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Hidden // SWAGGER에서 숨기기
@RestController
@RequestMapping("/api/v0/activity")
@RequiredArgsConstructor
public class ActivityController {
    private final ActivityService activityService;

    @GetMapping
    public ResponseDto<List<ActivityResponseDto>> getActivities() {
        List<ActivityResponseDto> activityResponseDtos = activityService.getActivities();

        return new ResponseDto<>(
                ResultCode.SUCCESS.getCode(),
                activityResponseDtos,
                ResultCode.SUCCESS.getMessage()
        );
    }

    @PostMapping
    public ResponseDto<?> addActivities(@RequestBody List<AddActivityRequestDto> addActivityRequestDtos) {
        List<Activity> activitiesWithoutId = addActivityRequestDtos.stream()
                .map(AddActivityRequestDto::toActivityWithoutId)
                .toList();

        activityService.addActivities(activitiesWithoutId);

        return new ResponseDto<>(
                ResultCode.SUCCESS.getCode(),
                ResultCode.SUCCESS.getMessage()
        );
    }

    @PutMapping
    public ResponseDto<?> updateActivities(@RequestBody List<UpdateActivityRequestDto> updateActivityRequestDtos) {
        List<Activity> activitiesWithId = updateActivityRequestDtos.stream()
                .map(UpdateActivityRequestDto::toActivityWithId)
                .toList();

        activityService.updateActivities(activitiesWithId);

        return new ResponseDto<>(
                ResultCode.SUCCESS.getCode(),
                ResultCode.SUCCESS.getMessage()
        );
    }

    @DeleteMapping
    public ResponseDto<?> removeActivity(@RequestBody RemoveActivityRequestDto removeActivityRequestDto) {
        activityService.removeActivityById(removeActivityRequestDto.getActivityId());

        return new ResponseDto<>(
                ResultCode.SUCCESS.getCode(),
                ResultCode.SUCCESS.getMessage()
        );
    }
}
