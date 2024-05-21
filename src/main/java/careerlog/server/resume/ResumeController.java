package careerlog.server.resume;


import careerlog.server.config.security.SecurityUtils;
import careerlog.server.resume.domain.ResumeItem;
import careerlog.server.resume.domain.ResumeType;
import careerlog.server.resume.dto.CreateResumeRequestDto;
import careerlog.server.resume.dto.ResumeTypeResponseDto;
import careerlog.server.resume.service.ResumeService;
import careerlog.server.user.domain.User;
import careerlog.server.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v0/resume")
@RequiredArgsConstructor
public class ResumeController {

    private final ResumeService resumeService;
    private final UserService userService;

    @GetMapping("/list")
    public List<ResumeTypeResponseDto> getResumeTypes() {
        List<ResumeType> resumeTypes = resumeService.getResumeTypes();

        return resumeTypes.stream()
                .map(ResumeTypeResponseDto::new)
                .toList();
    }

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

        return "ok";
    }
}
