package careerlog.server.careerboard;

import careerlog.server.careerboard.domain.CareerBoard;
import careerlog.server.careerboard.dto.request.save.SaveRequestDto;
import careerlog.server.careerboard.dto.response.CareerBoardResponseDto;
import careerlog.server.careerboard.service.CareerBoardService;
import careerlog.server.common.dto.ResponseDto;
import careerlog.server.common.exception.CustomException;
import careerlog.server.common.resultcode.ResultCode;
import careerlog.server.config.SecurityUtils;
import careerlog.server.user.domain.User;
import careerlog.server.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v0/career-board")
public class CareerBoardController {

    private final UserService userService;
    private final CareerBoardService careerBoardService;

    @GetMapping
    public ResponseDto<CareerBoardResponseDto> getCareerBoard() {
        User user = userService.getUserById(SecurityUtils.getCurrentUserId());
        CareerBoard careerBoard = careerBoardService.getCareerBoardByUser(user);
        CareerBoardResponseDto careerBoardResponseDto = CareerBoardResponseDto.toCareerBoardResponseDto(careerBoard);

        return new ResponseDto<>(
                ResultCode.SUCCESS.getCode(),
                careerBoardResponseDto,
                ResultCode.SUCCESS.getMessage()
        );
    }


    @PostMapping("/save/{saveType}")
    public ResponseDto<?> saveCareerBoardItems(
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

        return new ResponseDto<>(
                ResultCode.SUCCESS.getCode(),
                ResultCode.SUCCESS.getMessage()
        );
    }

}
