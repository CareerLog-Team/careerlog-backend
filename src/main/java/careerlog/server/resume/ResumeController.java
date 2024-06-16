package careerlog.server.resume;


import careerlog.server.config.security.SecurityUtils;
import careerlog.server.resume.domain.ResumeItem;
import careerlog.server.resume.domain.ResumeStyle;
import careerlog.server.resume.dto.CreateResumeRequestDto;
import careerlog.server.resume.dto.ResumeStyleResponseDto;
import careerlog.server.resume.service.ResumeService;
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

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v0/resume")
@RequiredArgsConstructor
@Tag(name = "이력서 관리 컨트롤러", description = "사용자가 생성한 이력서 관련 API 설정을 정의합니다.")
public class ResumeController {

    private final ResumeService resumeService;
    private final UserService userService;


    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "이력서 형식 리스트 조회 완료", content = @Content(schema = @Schema(implementation = ResumeStyleResponseDto.class))),
    })
    @Operation(summary = "이력서 형식 조회 API", description = "사용자가 이력서 생성에 활용할 수 있는 형식 리스트를 조회하는 API 입니다.")
    @GetMapping("/styles")
    public List<ResumeStyleResponseDto> getResumeStyles() {
        List<ResumeStyle> resumeStyles = resumeService.getResumeTypes();

        log.info("이력서 형식 리스트가 조회되었습니다.");
        log.debug("이력서 형식 개수 : {}개", resumeStyles.size());

        return resumeStyles.stream()
                .map(ResumeStyleResponseDto::new)
                .toList();
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "이력서 생성 완료", content = @Content(schema = @Schema(implementation = String.class))),
    })
    @Operation(summary = "이력서 생성 API", description = "이력서를 생성하는 API 입니다.")
    @PostMapping("/create")
    public String createResume(@RequestBody CreateResumeRequestDto createResumeRequestDto) {
        String userId = SecurityUtils.getCurrentUserId();
        User user = userService.getUserById(userId);

        List<CreateResumeRequestDto.ResumeItemDto> resumeItemDtos = createResumeRequestDto.getResumeItemDtos();
        List<ResumeItem> resumeItems = resumeItemDtos.stream()
                .map(CreateResumeRequestDto.ResumeItemDto::toResumeItem)
                .toList();

        String resumeTypeCode = createResumeRequestDto.getResumeTypeCode();

        resumeService.addResume(user, resumeItems, resumeTypeCode);

        log.info("이력서 생성이 완료되었습니다.");
        log.debug("""
                유저 ID : {}
                이력서 스타일 : {}
                이력서에 포함한 항목 개수 : {}
                """,
                userId,
                resumeTypeCode,
                resumeItems.size()
        );

        return "ok";
    }
}
