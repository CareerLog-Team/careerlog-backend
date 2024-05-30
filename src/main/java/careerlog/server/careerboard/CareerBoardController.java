package careerlog.server.careerboard;

import careerlog.server.careerboard.domain.*;
import careerlog.server.careerboard.dto.request.add.*;
import careerlog.server.careerboard.dto.request.remove.*;
import careerlog.server.careerboard.dto.request.save.*;
import careerlog.server.careerboard.dto.request.update.*;
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
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

/*
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
*/


    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "홛동 & 경험 저장 완료", content = @Content(schema = @Schema(implementation = SaveActivityRequestDto.class))),
    })
    @Operation(summary = "활동 & 경험 저장 API", description = "이력 관리 페이지에 활동 & 경험을 저장하는 API 입니다.")
    @PostMapping("/save/activity")
    public String saveActivity(@RequestBody SaveActivityRequestDto saveActivityRequestDto) {
        String userId = SecurityUtils.getCurrentUserId();
        User user = userService.getUserById(userId);
        CareerBoard careerBoard = careerBoardService.getCareerBoardByUser(user);

        // 1. Dto -> Domain 으로 변환
        List<RemoveActivityRequestDto> removeActivityRequestDtos = saveActivityRequestDto.getRemoveActivityRequestDtos();
        List<String> removeActivityIds = removeActivityRequestDtos.stream()
                .map(RemoveActivityRequestDto::getActivityId)
                .toList();

        List<UpdateActivityRequestDto> updateActivityRequestDtos = saveActivityRequestDto.getUpdateActivityRequestDtos();
        List<Activity> updateActivities = updateActivityRequestDtos.stream()
                .map(UpdateActivityRequestDto::toActivityWithId)
                .toList();

        List<AddActivityRequestDto> addActivityRequestDtos = saveActivityRequestDto.getAddActivityRequestDtos();
        List<Activity> addActivities = addActivityRequestDtos.stream()
                .map(AddActivityRequestDto::toActivityWithoutId)
                .toList();

        careerBoardService.saveActivity(
                careerBoard,
                removeActivityIds,
                updateActivities,
                addActivities
        );

        return "ok";
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "이력 저장 완료", content = @Content(schema = @Schema(implementation = SaveCareerRequestDto.class))),
    })
    @Operation(summary = "이력 저장 API", description = "이력 관리 페이지에 이력을 저장하는 API 입니다.")
    @PostMapping("/save/career")
    public String saveCareer(@RequestBody SaveCareerRequestDto saveCareerRequestDto) {
        String userId = SecurityUtils.getCurrentUserId();
        User user = userService.getUserById(userId);
        CareerBoard careerBoard = careerBoardService.getCareerBoardByUser(user);

        // 1. Dto -> Domain 으로 변환
        List<RemoveCareerRequestDto> removeCareerRequestDtos = saveCareerRequestDto.getRemoveCareerRequestDtos();
        List<String> removeCareerIds = removeCareerRequestDtos.stream()
                .map(RemoveCareerRequestDto::getCareerId)
                .toList();

        List<UpdateCareerRequestDto> updateCareerRequestDtos = saveCareerRequestDto.getUpdateCareerRequestDtos();
        List<Career> updateCareers = updateCareerRequestDtos.stream()
                .map(UpdateCareerRequestDto::toCareerEntityWithId)
                .toList();

        List<AddCareerRequestDto> addCareerRequestDtos = saveCareerRequestDto.getAddCareerRequestDtos();
        List<Career> addCareers = addCareerRequestDtos.stream()
                .map(AddCareerRequestDto::toCareerEntityWithoutId)
                .toList();

        careerBoardService.saveCareer(
                careerBoard,
                removeCareerIds,
                updateCareers,
                addCareers
        );

        return "ok";
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "자격 저장 완료", content = @Content(schema = @Schema(implementation = SaveCertificateRequestDto.class))),
    })
    @Operation(summary = "자격 저장 API", description = "이력 관리 페이지에 자격을 저장하는 API 입니다.")
    @PostMapping("/save/certificate")
    public String saveCertificate(@RequestBody SaveCertificateRequestDto saveCertificateRequestDto) {
        String userId = SecurityUtils.getCurrentUserId();
        User user = userService.getUserById(userId);
        CareerBoard careerBoard = careerBoardService.getCareerBoardByUser(user);

        // 1. Dto -> Domain 으로 변환
        List<RemoveCertificateRequestDto> removeCertificateRequestDtos = saveCertificateRequestDto.getRemoveCertificateRequestDtos();
        List<String> removeCertificateIds = removeCertificateRequestDtos.stream()
                .map(RemoveCertificateRequestDto::getCertificateId)
                .toList();

        List<UpdateCertificateRequestDto> updateCertificateRequestDtos = saveCertificateRequestDto.getUpdateCertificateRequestDtos();
        List<Certificate> updateCertificates = updateCertificateRequestDtos.stream()
                .map(UpdateCertificateRequestDto::toCertificateWithId)
                .toList();

        List<AddCertificateRequestDto> addCertificateRequestDtos = saveCertificateRequestDto.getAddCertificateRequestDtos();
        List<Certificate> addCertificates = addCertificateRequestDtos.stream()
                .map(AddCertificateRequestDto::toCertificateWithoutId)
                .toList();

        careerBoardService.saveCertificate(
                careerBoard,
                removeCertificateIds,
                updateCertificates,
                addCertificates
        );

        return "ok";
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "학력 저장 완료", content = @Content(schema = @Schema(implementation = SaveEducationRequestDto.class))),
    })
    @Operation(summary = "학력 저장 API", description = "이력 관리 페이지에 학력을 저장하는 API 입니다.")
    @PostMapping("/save/education")
    public String saveEducation(@RequestBody SaveEducationRequestDto saveEducationRequestDto) {
        String userId = SecurityUtils.getCurrentUserId();
        User user = userService.getUserById(userId);
        CareerBoard careerBoard = careerBoardService.getCareerBoardByUser(user);

        // 1. Dto -> Domain 으로 변환
        List<RemoveEducationRequestDto> removeEducationRequestDtos = saveEducationRequestDto.getRemoveEducationRequestDtos();
        List<String> removeEducationIds = removeEducationRequestDtos.stream()
                .map(RemoveEducationRequestDto::getEducationId)
                .toList();

        List<UpdateEducationRequestDto> updateEducationRequestDtos = saveEducationRequestDto.getUpdateEducationRequestDtos();
        List<Education> updateEducations = updateEducationRequestDtos.stream()
                .map(UpdateEducationRequestDto::toEducationWithId)
                .toList();

        List<AddEducationRequestDto> addEducationRequestDtos = saveEducationRequestDto.getAddEducationRequestDtos();
        List<Education> addEducations = addEducationRequestDtos.stream()
                .map(AddEducationRequestDto::toEducationWithoutId)
                .toList();

        careerBoardService.saveEducation(
                careerBoard,
                removeEducationIds,
                updateEducations,
                addEducations
        );

        return "ok";
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "외국어, 스킬, 링크 저장 완료", content = @Content(schema = @Schema(implementation = SaveAutoRequestDto.class))),
    })
    @Operation(summary = "외국어, 스킬, 링크 (자동)저장 API", description = "이력 관리 페이지에 외국어, 스킬, 링크을 (자동)저장하는 API 입니다.")
    @PostMapping("/save/auto")
    public String saveAuto(@RequestBody SaveAutoRequestDto saveAutoRequestDto) {
        String userId = SecurityUtils.getCurrentUserId();
        User user = userService.getUserById(userId);
        CareerBoard careerBoard = careerBoardService.getCareerBoardByUser(user);

        if (!saveAutoRequestDto.checkLanguageIsNull()) {
            SaveLanguageRequestDto saveLanguageRequestDto = saveAutoRequestDto.getSaveLanguageRequestDto();
            requestSaveLanguage(careerBoard, saveLanguageRequestDto);
        }

        if (!saveAutoRequestDto.checkLinkIsNull()) {
            SaveLinkRequestDto saveLinkRequestDto = saveAutoRequestDto.getSaveLinkRequestDto();
            requestSaveLink(careerBoard, saveLinkRequestDto);
        }

        if (!saveAutoRequestDto.checkSkillIsNull()) {
            List<String> saveSkills = saveAutoRequestDto.getSkills();
            careerBoardService.saveSkills(careerBoard, saveSkills);
        }

        return "ok";
    }

    private void requestSaveLanguage(CareerBoard careerBoard, SaveLanguageRequestDto saveLanguageRequestDto) {
        List<RemoveLanguageRequestDto> removeLanguageRequestDtos = saveLanguageRequestDto.getRemoveLanguageRequestDtos();
        List<String> removeLanguageIds = removeLanguageRequestDtos.stream()
                .map(RemoveLanguageRequestDto::getLanguageId)
                .toList();

        List<UpdateLanguageRequestDto> updateLanguageRequestDtos = saveLanguageRequestDto.getUpdateLanguageRequestDtos();
        List<Language> updateLanguages = updateLanguageRequestDtos.stream()
                .map(UpdateLanguageRequestDto::toLanguageWithId)
                .toList();

        List<AddLanguageRequestDto> addLanguageRequestDtos = saveLanguageRequestDto.getAddLanguageRequestDtos();
        List<Language> addLanguages = addLanguageRequestDtos.stream()
                .map(AddLanguageRequestDto::toLanguageWithoutId)
                .toList();

        careerBoardService.saveLanguage(
                careerBoard,
                removeLanguageIds,
                updateLanguages,
                addLanguages
        );
    }

    private void requestSaveLink(CareerBoard careerBoard, SaveLinkRequestDto saveLinkRequestDto) {
        List<RemoveLinkRequestDto> removeLinkRequestDtos = saveLinkRequestDto.getRemoveLinkRequestDtos();
        List<String> removeLinkIds = removeLinkRequestDtos.stream()
                .map(RemoveLinkRequestDto::getLinkId)
                .toList();

        List<UpdateLinkRequestDto> updateLinkRequestDtos = saveLinkRequestDto.getUpdateLinkRequestDtos();
        List<Link> updateLinks = updateLinkRequestDtos.stream()
                .map(UpdateLinkRequestDto::toLinkWithId)
                .toList();

        List<AddLinkRequestDto> addLinkRequestDtos =  saveLinkRequestDto.getAddLinkRequestDtos();
        List<Link> addLinks = addLinkRequestDtos.stream()
                .map(AddLinkRequestDto::toLinkWithoutId)
                .toList();

        careerBoardService.saveLink(
                careerBoard,
                removeLinkIds,
                updateLinks,
                addLinks
        );
    }




}
