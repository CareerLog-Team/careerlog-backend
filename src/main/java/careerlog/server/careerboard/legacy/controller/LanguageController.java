package careerlog.server.careerboard.legacy.controller;


import careerlog.server.common.dto.ResponseDto;
import careerlog.server.common.response.resultcode.ResultCode;
import careerlog.server.careerboard.domain.Language;
import careerlog.server.careerboard.dto.request.add.AddLanguageRequestDto;
import careerlog.server.careerboard.dto.request.remove.RemoveLanguageRequestDto;
import careerlog.server.careerboard.dto.request.update.UpdateLanguageRequestDto;
import careerlog.server.careerboard.dto.response.LanguageResponseDto;
import careerlog.server.careerboard.service.LanguageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v0/language")
@RequiredArgsConstructor
public class LanguageController {

    private final LanguageService languageService;

    @GetMapping
    public ResponseDto<List<LanguageResponseDto>> getLanguages() {
        List<LanguageResponseDto> languages = languageService.getLanguages();

        return new ResponseDto<>(
                ResultCode.SUCCESS.getCode(),
                languages,
                ResultCode.SUCCESS.getMessage()
        );
    }

    @PostMapping
    public ResponseDto<?> addLanguages(@RequestBody List<AddLanguageRequestDto> addLanguageRequestDtos) {
        List<Language> languagesWithoutId = addLanguageRequestDtos.stream()
                .map(AddLanguageRequestDto::toLanguageWithoutId)
                .toList();

        languageService.addLanguages(languagesWithoutId);

        return new ResponseDto<>(
                ResultCode.SUCCESS.getCode(),
                ResultCode.SUCCESS.getMessage()
        );
    }

    @PutMapping
    public ResponseDto<?> updateLanguages(@RequestBody List<UpdateLanguageRequestDto> updateLanguageRequestDtos) {
        List<Language> languagesWithId = updateLanguageRequestDtos.stream()
                .map(UpdateLanguageRequestDto::toLanguageWithId)
                .toList();

        languageService.updateLanguages(languagesWithId);

        return new ResponseDto<>(
                ResultCode.SUCCESS.getCode(),
                ResultCode.SUCCESS.getMessage()
        );
    }

    @DeleteMapping
    public ResponseDto<?> removeLanguage(@RequestBody RemoveLanguageRequestDto removeLanguageRequestDto) {
        languageService.removeLanguage(removeLanguageRequestDto.getLanguageId());

        return new ResponseDto<>(
                ResultCode.SUCCESS.getCode(),
                ResultCode.SUCCESS.getMessage()
        );
    }
}
