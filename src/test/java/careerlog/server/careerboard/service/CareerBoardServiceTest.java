package careerlog.server.careerboard.service;

import careerlog.server.careerboard.domain.Activity;
import careerlog.server.careerboard.domain.CareerBoard;
import careerlog.server.careerboard.dto.request.add.AddActivityRequestDto;
import careerlog.server.careerboard.dto.request.remove.RemoveActivityRequestDto;
import careerlog.server.careerboard.dto.request.update.UpdateActivityRequestDto;
import careerlog.server.user.domain.User;
import careerlog.server.user.service.UserService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@Transactional
@Rollback(value = false)
class CareerBoardServiceTest {

    @Autowired
    CareerBoardService careerBoardService;

    @Autowired
    UserService userService;

    @Test
    void 활동_저장_테스트() {
        //given
        User user = User.builder().build();
        userService.addUser(user);
        careerBoardService.addCareerBoard(user);

        AddActivityRequestDto activity = AddActivityRequestDto.builder()
                .activityName("activity1")
                .agency("test")
                .result("test")
                .startDate("2021-03-01")
                .endDate("2021-03-02")
                .build();

        List<RemoveActivityRequestDto> removeActivityRequestDtos = new ArrayList<>();
        List<UpdateActivityRequestDto> updateActivityRequestDtos = new ArrayList<>();
        List<AddActivityRequestDto> addActivityRequestDtos = new ArrayList<>();
        addActivityRequestDtos.add(activity);

        careerBoardService.saveActivity2(
                user,
                removeActivityRequestDtos,
                updateActivityRequestDtos,
                addActivityRequestDtos
        );

        //when
        CareerBoard findCareerBoard = careerBoardService.getCareerBoardByUser(user);
        List<Activity> activities = findCareerBoard.getActivities();

        for (Activity findActivity : activities) {
            System.out.println("findActivity = " + findActivity.getActivityId());
            System.out.println("findActivity = " + findActivity.getActivityName());
            System.out.println("findActivity = " + findActivity.getResult());
            System.out.println("findActivity = " + findActivity.getAgency());
            System.out.println("findActivity = " + findActivity.getStartDate());
            System.out.println("findActivity = " + findActivity.getEndDate());
        }

        Assertions.assertThat(activities.size()).isEqualTo(1);
    }
}