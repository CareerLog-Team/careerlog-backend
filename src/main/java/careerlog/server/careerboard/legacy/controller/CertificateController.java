package careerlog.server.careerboard.legacy.controller;


import careerlog.server.common.dto.ResponseDto;
import careerlog.server.common.response.resultcode.ResultCode;
import careerlog.server.careerboard.domain.Certificate;
import careerlog.server.careerboard.dto.request.add.AddCertificateRequestDto;
import careerlog.server.careerboard.dto.request.remove.RemoveCertificateRequestDto;
import careerlog.server.careerboard.dto.request.update.UpdateCertificateRequestDto;
import careerlog.server.careerboard.dto.response.CertificateResponseDto;
import careerlog.server.careerboard.service.CertificateService;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Hidden
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v0/certificate")
public class CertificateController {

    private final CertificateService certificateService;

    @GetMapping
    public ResponseDto<List<CertificateResponseDto>> getCertificates() {
        List<CertificateResponseDto> certificates = certificateService.getCertificates();

        return new ResponseDto<>(
                ResultCode.SUCCESS.getCode(),
                certificates,
                ResultCode.SUCCESS.getMessage()
        );
    }

    @PostMapping
    public ResponseDto<?> addCertificates(@RequestBody List<AddCertificateRequestDto> addCertificateRequestDtos) {
        List<Certificate> certificatesWithoutId = addCertificateRequestDtos.stream()
                .map(AddCertificateRequestDto::toCertificateWithoutId)
                .toList();

        certificateService.addCertificates(certificatesWithoutId);

        return new ResponseDto<>(
                ResultCode.SUCCESS.getCode(),
                ResultCode.SUCCESS.getMessage()
        );
    }

    @PutMapping
    public ResponseDto<?> updateCertificates(@RequestBody List<UpdateCertificateRequestDto> updateCertificateRequestDtos) {
        List<Certificate> certificatesWithId = updateCertificateRequestDtos.stream()
                .map(UpdateCertificateRequestDto::toCertificateWithId)
                .toList();

        certificateService.updateCertificates(certificatesWithId);

        return new ResponseDto<>(
                ResultCode.SUCCESS.getCode(),
                ResultCode.SUCCESS.getMessage()
        );
    }

    @DeleteMapping
    public ResponseDto<?> removeCertificate(@RequestBody RemoveCertificateRequestDto removeCertificateRequestDto) {
        certificateService.removeCertificate(removeCertificateRequestDto.getCertificateId());

        return new ResponseDto<>(
                ResultCode.SUCCESS.getCode(),
                ResultCode.SUCCESS.getMessage()
        );
    }
}
