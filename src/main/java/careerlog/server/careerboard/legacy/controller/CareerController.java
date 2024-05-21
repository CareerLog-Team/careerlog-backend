package careerlog.server.careerboard.legacy.controller;


import careerlog.server.common.dto.ResponseDto;
import careerlog.server.common.response.resultcode.ResultCode;
import careerlog.server.careerboard.domain.Career;
import careerlog.server.careerboard.dto.request.add.AddCareerRequestDto;
import careerlog.server.careerboard.dto.response.CareerResponseDto;
import careerlog.server.careerboard.dto.request.remove.RemoveCareerRequestDto;
import careerlog.server.careerboard.dto.request.update.UpdateCareerRequestDto;
import careerlog.server.careerboard.service.CareerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v0/career")
public class CareerController {

    private final CareerService careerService;

    @GetMapping
    public ResponseDto<List<CareerResponseDto>> getCareers() {
        // 사용자 정보는 Spring security로 가져오기
        List<CareerResponseDto> careers = careerService.getCareers();

        return new ResponseDto<>(
                ResultCode.SUCCESS.getCode(),
                careers,
                ResultCode.SUCCESS.getMessage()
        );
    }

    @PostMapping
    public ResponseDto<?> addCareers(@RequestBody List<AddCareerRequestDto> addCareerRequestDtos) {
        List<Career> careersWithoutId = addCareerRequestDtos.stream()
                .map(AddCareerRequestDto::toCareerEntityWithoutId)
                .toList();

        // TODO: 사용자 ID 추가 필요
        careerService.addCareers(careersWithoutId);

        return new ResponseDto<>(
                ResultCode.SUCCESS.getCode(),
                ResultCode.SUCCESS.getMessage()
        );
    }

    @PutMapping
    public ResponseDto<?> updateCareers(@RequestBody List<UpdateCareerRequestDto> updateCareerRequestDtos){
        List<Career> careersWithId = updateCareerRequestDtos.stream()
                .map(UpdateCareerRequestDto::toCareerEntityWithId)
                .toList();

        // TODO: 사용자 ID 추가 필요
        careerService.updateCareers(careersWithId);

        return new ResponseDto<>(
                ResultCode.SUCCESS.getCode(),
                ResultCode.SUCCESS.getMessage()
        );
    }

    @DeleteMapping
    public ResponseDto<?> removeCareer(@RequestBody RemoveCareerRequestDto removeCareerRequestDto) {
        careerService.removeCareer(removeCareerRequestDto.getCareerId());

        return new ResponseDto<>(
                ResultCode.SUCCESS.getCode(),
                ResultCode.SUCCESS.getMessage()
        );
    }

}
