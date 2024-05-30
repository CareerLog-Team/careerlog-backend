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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v0/work-record")
@Tag(name = "업무경력 관리 컨트롤러", description = "사용자가 생성한 업무경력 관련 API 설정을 정의합니다.")
public class WorkRecordController {

    private final WorkRecordService workRecordService;
    private final UserService userService;
    private final CareerService careerService;
    private final CareerBoardService careerBoardService;

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "업무 경력 리스트 조회 완료", content = @Content(schema = @Schema(implementation = WorkRecordResponseDto.class))),
    })
    @Operation(summary = "업무 경력 리스트 조회 API", description = "사용자가 생성한 업무 경력 리스트를 조회하는 API 입니다.")
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

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "업무 경력 리스트 생성 완료", content = @Content(schema = @Schema(implementation = String.class))),
    })
    @Operation(summary = "업무 경력 생성 API", description = "사용자의 업무 경력을 생성하는 API 입니다.")
    @PostMapping("/create")
    public String addWorkRecord(@RequestBody AddWorkRecordRequestDto addWorkRecordRequestDto) {
        Career career = careerService.getCareerById(addWorkRecordRequestDto.getCareerId());
        WorkRecord addWorkRecord = addWorkRecordRequestDto.toWorkRecordWithoutId();

        workRecordService.addWorkRecord(career, addWorkRecord);
        
        return "ok";
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "업무 경력 리스트 수정 완료", content = @Content(schema = @Schema(implementation = String.class))),
    })
    @Operation(summary = "업무 경력 수정 API", description = "사용자의 업무 경력을 수정하는 API 입니다.")
    @PutMapping("/update")
    public String updateWorkRecord(@RequestBody UpdateWorkRecordRequestDto updateWorkRecordRequestDto) {
        WorkRecord beforeWorkRecord = workRecordService.getWorkRecord(updateWorkRecordRequestDto.getWorkRecordId());
        WorkRecord updateWorkRecord = updateWorkRecordRequestDto.toWorkRecordWithoutId();

        workRecordService.updateWorkRecord(beforeWorkRecord, updateWorkRecord);

        return "ok";
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "업무 경력 리스트 제거 완료", content = @Content(schema = @Schema(implementation = String.class))),
    })
    @Operation(summary = "업무 경력 제거 API", description = "사용자의 업무 경력을 제거하는 API 입니다.")
    @DeleteMapping("remove")
    public String removeWorkRecord(@RequestBody RemoveWorkRecordDto removeWorkRecordDto) {
        WorkRecord removeWorkRecord = workRecordService.getWorkRecord(removeWorkRecordDto.getWorkRecordId());
        workRecordService.removeWorkRecord(removeWorkRecord);

        return "ok";
    }
}
