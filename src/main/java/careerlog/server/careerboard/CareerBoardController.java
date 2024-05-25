package careerlog.server.careerboard;

import careerlog.server.careerboard.domain.CareerBoard;
import careerlog.server.careerboard.dto.request.save.SaveRequestDto;
import careerlog.server.careerboard.dto.response.CareerBoardResponseDto;
import careerlog.server.careerboard.service.CareerBoardService;
import careerlog.server.common.response.exception.CustomException;
import careerlog.server.common.response.resultcode.ResultCode;
import careerlog.server.config.security.SecurityUtils;
import careerlog.server.user.domain.User;
import careerlog.server.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v0/career-board")
@Tag(name = "이력 관리 컨트롤러", description = "이력 관리 관련 API 설정을 정의합니다.")
public class CareerBoardController {

    private final UserService userService;
    private final CareerBoardService careerBoardService;

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "사용자 이력 조회 완료", content = @Content(schema = @Schema(implementation = CareerBoardResponseDto.class))),
    })
    @Operation(summary = "이력 조회 API", description = "이력 관리 페이지에 해당하는 정보를 반환하는 API 입니다.")
    @GetMapping
    public CareerBoardResponseDto getCareerBoard() {
        User user = userService.getUserById(SecurityUtils.getCurrentUserId());
        CareerBoard careerBoard = careerBoardService.getCareerBoardByUser(user);
        return CareerBoardResponseDto.toCareerBoardResponseDto(careerBoard);
    }


    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "변경사항 저장 완료", content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "500", description = "잘못된 경로를 요청했습니다", content = @Content(schema = @Schema(implementation = CustomException.class))),
    })
    @Operation(
            summary = "변경 이력 저장 API",
            description = "이력 관리 페이지에 해당하는 정보를 수정할 때 활용하는 API 입니다. saveType에는 5가지 케이스가 존재합니다." +
                    "<br> [career, activity, education, certificate, auto] 이렇게 5가지로 구성되며, 다른 값 입력시 Exception이 반환됩니다." +
                    "<br> 각 항목에 해당하는 Dto를 입력하여 반환해주시면 됩니다."
    )
    @PostMapping("/save/{saveType}")
    public String saveCareerBoardItems(
            @PathVariable String saveType,
            @RequestBody SaveRequestDto saveRequestDto) {

        User user = userService.getUserById(SecurityUtils.getCurrentUserId());
        CareerBoard careerBoard = careerBoardService.getCareerBoardByUser(user);

        switch (saveType.toLowerCase()) {
            case "career":
                assert saveRequestDto.getSaveCareerRequestDto() != null : "수정할 항목 [Career] 값이 포함되지 않았습니다";

                careerBoardService.saveCareer(
                        careerBoard,
                        saveRequestDto.getSaveCareerRequestDto().getRemoveCareerRequestDtos(),
                        saveRequestDto.getSaveCareerRequestDto().getUpdateCareerRequestDtos(),
                        saveRequestDto.getSaveCareerRequestDto().getAddCareerRequestDtos()
                );
                break;

            case "activity":
                assert saveRequestDto.getSaveActivityRequestDto() != null : "수정할 항목 [Activity] 값이 포함되지 않았습니다";

                careerBoardService.saveActivity(
                        careerBoard,
                        saveRequestDto.getSaveActivityRequestDto().getRemoveActivityRequestDtos(),
                        saveRequestDto.getSaveActivityRequestDto().getUpdateActivityRequestDtos(),
                        saveRequestDto.getSaveActivityRequestDto().getAddActivityRequestDtos()
                );
                break;

            case "education":
                assert saveRequestDto.getSaveEducationRequestDto() != null : "수정할 항목 [Education] 값이 포함되지 않았습니다";

                careerBoardService.saveEducation(
                        careerBoard,
                        saveRequestDto.getSaveEducationRequestDto().getRemoveEducationRequestDtos(),
                        saveRequestDto.getSaveEducationRequestDto().getUpdateEducationRequestDtos(),
                        saveRequestDto.getSaveEducationRequestDto().getAddEducationRequestDtos()
                );
                break;

            case "certificate":
                assert saveRequestDto.getSaveCertificateRequestDto() != null : "수정할 항목 [Certificate] 값이 포함되지 않았습니다";

                careerBoardService.saveCertificate(
                        careerBoard,
                        saveRequestDto.getSaveCertificateRequestDto().getRemoveCertificateRequestDtos(),
                        saveRequestDto.getSaveCertificateRequestDto().getUpdateCertificateRequestDtos(),
                        saveRequestDto.getSaveCertificateRequestDto().getAddCertificateRequestDtos()
                );

                break;

            case "auto":
                assert saveRequestDto.getSaveAutoRequestDto() != null : "수정할 항목 [Auto(Language, Link, Skill)] 값이 포함되지 않았습니다";

                careerBoardService.saveAuto(
                        careerBoard,
                        saveRequestDto.getSaveAutoRequestDto()
                );

            default:
                throw new CustomException(ResultCode.INTERNAL_SERVER_ERROR);
        }

        return "ok";
    }

}
