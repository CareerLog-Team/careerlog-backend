package careerlog.server.careerboard.legacy.controller;


import careerlog.server.common.dto.ResponseDto;
import careerlog.server.common.response.resultcode.ResultCode;
import careerlog.server.careerboard.domain.Education;
import careerlog.server.careerboard.dto.request.add.AddEducationRequestDto;
import careerlog.server.careerboard.dto.request.remove.RemoveEducationRequestDto;
import careerlog.server.careerboard.dto.request.update.UpdateEducationRequestDto;
import careerlog.server.careerboard.dto.response.EducationResponseDto;
import careerlog.server.careerboard.service.EducationService;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Hidden
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v0/education")
public class EducationController {

    private final EducationService educationService;

    @GetMapping
    public ResponseDto<List<EducationResponseDto>> getEducations() {
        List<EducationResponseDto> educations = educationService.getEducations();

        return new ResponseDto<>(
                ResultCode.SUCCESS.getCode(),
                educations,
                ResultCode.SUCCESS.getMessage()
        );
    }

    @PostMapping
    public ResponseDto<?> addEducations(@RequestBody List<AddEducationRequestDto> addEducationRequestDtos) {
        List<Education> educationsWithoutId = addEducationRequestDtos.stream()
                .map(AddEducationRequestDto::toEducationWithoutId)
                .toList();

        educationService.addEducations(educationsWithoutId);

        return new ResponseDto<>(
                ResultCode.SUCCESS.getCode(),
                ResultCode.SUCCESS.getMessage()
        );
    }

    @PutMapping
    public ResponseDto<?> updateEducations(@RequestBody List<UpdateEducationRequestDto> updateEducationRequestDtos) {
        List<Education> educationsWithId = updateEducationRequestDtos.stream()
                .map(UpdateEducationRequestDto::toEducationWithId)
                .toList();

        educationService.updateEducations(educationsWithId);

        return new ResponseDto<>(
                ResultCode.SUCCESS.getCode(),
                ResultCode.SUCCESS.getMessage()
        );
    }

    @DeleteMapping
    public ResponseDto<?> removeEducation(@RequestBody RemoveEducationRequestDto removeEducationRequestDto) {
        educationService.removeEducation(removeEducationRequestDto.getEducationId());

        return new ResponseDto<>(
                ResultCode.SUCCESS.getCode(),
                ResultCode.SUCCESS.getMessage()
        );
    }

}
