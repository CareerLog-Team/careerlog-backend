package careerlog.server.careerboard;

import careerlog.server.careerboard.domain.*;
import careerlog.server.careerboard.dto.request.save.*;
import careerlog.server.careerboard.dto.response.CareerBoardResponseDto;
import careerlog.server.careerboard.service.CareerBoardService;
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
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;


@Slf4j
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

        log.info("사용자 이력이 조회되었습니다.");
        log.debug(
                """
                유저 ID : {}
                커리어보드 ID : {}
                """,
                user.getUserId(),
                careerBoard.getCareerBoardId()
        );

        return CareerBoardResponseDto.toCareerBoardResponseDto(careerBoard);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "홛동 & 경험 저장 완료", content = @Content(schema = @Schema(implementation = SaveActivityRequestDto.class))),
    })
    @Operation(summary = "활동 & 경험 저장 API", description = "이력 관리 페이지에 활동 & 경험을 저장하는 API 입니다.")
    @PostMapping("/save/activity")
    public String saveActivity(@RequestBody SaveActivityRequestDto saveActivityRequestDto) {
        careerBoardService.saveActivity(
                userService.getUserById(SecurityUtils.getCurrentUserId()),
                saveActivityRequestDto.getRemoveActivityRequestDtos(),
                saveActivityRequestDto.getUpdateActivityRequestDtos(),
                saveActivityRequestDto.getAddActivityRequestDtos()
        );

        log.info("활동 & 경험이 저장되었습니다.");

        return "ok";
    }


    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "이력 저장 완료", content = @Content(schema = @Schema(implementation = SaveCareerRequestDto.class))),
    })
    @Operation(summary = "이력 저장 API", description = "이력 관리 페이지에 이력을 저장하는 API 입니다.")
    @PostMapping("/save/career")
    public String saveCareer(@RequestBody SaveCareerRequestDto saveCareerRequestDto) {
        careerBoardService.saveCareer(
                userService.getUserById(SecurityUtils.getCurrentUserId()),
                saveCareerRequestDto.getRemoveCareerRequestDtos(),
                saveCareerRequestDto.getUpdateCareerRequestDtos(),
                saveCareerRequestDto.getAddCareerRequestDtos()
        );

        return "ok";
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "자격 저장 완료", content = @Content(schema = @Schema(implementation = SaveCertificateRequestDto.class))),
    })
    @Operation(summary = "자격 저장 API", description = "이력 관리 페이지에 자격을 저장하는 API 입니다.")
    @PostMapping("/save/certificate")
    public String saveCertificate(@RequestBody SaveCertificateRequestDto saveCertificateRequestDto) {
        careerBoardService.saveCertificate(
                userService.getUserById(SecurityUtils.getCurrentUserId()),
                saveCertificateRequestDto.getRemoveCertificateRequestDtos(),
                saveCertificateRequestDto.getUpdateCertificateRequestDtos(),
                saveCertificateRequestDto.getAddCertificateRequestDtos()
        );

        return "ok";
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "학력 저장 완료", content = @Content(schema = @Schema(implementation = SaveEducationRequestDto.class))),
    })
    @Operation(summary = "학력 저장 API", description = "이력 관리 페이지에 학력을 저장하는 API 입니다.")
    @PostMapping("/save/education")
    public String saveEducation(@RequestBody SaveEducationRequestDto saveEducationRequestDto) {
        careerBoardService.saveEducation(
                userService.getUserById(SecurityUtils.getCurrentUserId()),
                saveEducationRequestDto.getRemoveEducationRequestDtos(),
                saveEducationRequestDto.getUpdateEducationRequestDtos(),
                saveEducationRequestDto.getAddEducationRequestDtos()
        );

        return "ok";
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "외국어, 스킬, 링크 저장 완료", content = @Content(schema = @Schema(implementation = SaveAutoRequestDto.class))),
    })
    @Operation(summary = "외국어, 스킬, 링크 (자동)저장 API", description = "이력 관리 페이지에 외국어, 스킬, 링크을 (자동)저장하는 API 입니다.")
    @PostMapping("/save/auto")
    public String saveAuto(@RequestBody SaveAutoRequestDto saveAutoRequestDto) {
        User user = userService.getUserById(SecurityUtils.getCurrentUserId());

        if (!saveAutoRequestDto.checkLanguageIsNull()) {
            SaveLanguageRequestDto saveLanguageRequestDto = saveAutoRequestDto.getSaveLanguageRequestDto();
            careerBoardService.saveLanguage(
                    user,
                    saveLanguageRequestDto.getRemoveLanguageRequestDtos(),
                    saveLanguageRequestDto.getUpdateLanguageRequestDtos(),
                    saveLanguageRequestDto.getAddLanguageRequestDtos()
            );
        }

        if (!saveAutoRequestDto.checkLinkIsNull()) {
            SaveLinkRequestDto saveLinkRequestDto = saveAutoRequestDto.getSaveLinkRequestDto();
            careerBoardService.saveLink(
                    user,
                    saveLinkRequestDto.getRemoveLinkRequestDtos(),
                    saveLinkRequestDto.getUpdateLinkRequestDtos(),
                    saveLinkRequestDto.getAddLinkRequestDtos()
            );
        }

        if (!saveAutoRequestDto.checkSkillIsNull()) {
            careerBoardService.saveSkills(user, saveAutoRequestDto.getSkills());
        }

        return "ok";
    }
}
