package careerlog.server.workrecord;

import careerlog.server.careerboard.domain.Career;
import careerlog.server.careerboard.domain.CareerBoard;
import careerlog.server.careerboard.service.CareerBoardService;
import careerlog.server.careerboard.service.CareerService;
import careerlog.server.config.security.SecurityUtils;
import careerlog.server.user.domain.User;
import careerlog.server.user.service.UserService;
import careerlog.server.workrecord.domain.WorkRecord;
import careerlog.server.workrecord.dto.AddWorkRecordRequestDto;
import careerlog.server.workrecord.dto.RemoveWorkRecordDto;
import careerlog.server.workrecord.dto.UpdateWorkRecordRequestDto;
import careerlog.server.workrecord.dto.WorkRecordResponseDto;
import careerlog.server.workrecord.service.WorkRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v0/work-record")
public class WorkRecordController {

    private final WorkRecordService workRecordService;
    private final UserService userService;
    private final CareerService careerService;
    private final CareerBoardService careerBoardService;

    @GetMapping
    public List<WorkRecordResponseDto> getWorkRecord() {
        User user = userService.getUserById(SecurityUtils.getCurrentUserId());
        CareerBoard careerBoard = careerBoardService.getCareerBoardByUser(user);

        List<WorkRecord> workRecords = workRecordService.getWorkRecordsByCareers(careerBoard.getCareers());

        // workRecordDto 반환
        return workRecords.stream()
                .map(WorkRecordResponseDto::new)
                .toList();
    }

    @PostMapping
    public String addWorkRecord(@RequestBody AddWorkRecordRequestDto addWorkRecordRequestDto) {
        Career career = careerService.getCareer(addWorkRecordRequestDto.getCareerId());
        WorkRecord addWorkRecord = addWorkRecordRequestDto.toWorkRecordWithoutId();

        workRecordService.addWorkRecord(career, addWorkRecord);
        
        return "ok";
    }

    @PutMapping
    public String updateWorkRecord(@RequestBody UpdateWorkRecordRequestDto updateWorkRecordRequestDto) {
        WorkRecord beforeWorkRecord = workRecordService.getWorkRecord(updateWorkRecordRequestDto.getWorkRecordId());
        WorkRecord updateWorkRecord = updateWorkRecordRequestDto.toWorkRecordWithoutId();

        workRecordService.updateWorkRecord(beforeWorkRecord, updateWorkRecord);

        return "ok";
    }

    @DeleteMapping
    public String removeWorkRecord(@RequestBody RemoveWorkRecordDto removeWorkRecordDto) {
        WorkRecord removeWorkRecord = workRecordService.getWorkRecord(removeWorkRecordDto.getWorkRecordId());
        workRecordService.removeWorkRecord(removeWorkRecord);

        return "ok";
    }
}
