package careerlog.server.file;


import careerlog.server.careerboard.dto.response.CareerBoardResponseDto;
import careerlog.server.config.security.SecurityUtils;
import careerlog.server.file.domain.File;
import careerlog.server.file.dto.*;
import careerlog.server.file.service.FileService;
import careerlog.server.file.service.S3FileService;
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
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@Slf4j
@RestController
@RequestMapping("/api/v0/file")
@RequiredArgsConstructor
@Tag(name = "파일 관리 컨트롤러", description = "AWS S3와 연결되어있으며, 사용자가 업로드한 파일 관련 API 설정을 정의합니다.")
public class FileController {

    private final FileService fileService;
    private final S3FileService s3FileService;
    private final UserService userService;

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "사용자 파일 리스트 이력 조회 완료", content = @Content(schema = @Schema(implementation = FileResponseDto.class))),
    })
    @Operation(summary = "파일 리스트 조회 API", description = "사용자가 업로드한 파일의 리스트를 조회하는 API 입니다.")
    @GetMapping
    public List<FileResponseDto> getFiles() {
        User user = userService.getUserById(SecurityUtils.getCurrentUserId());
        List<File> files = fileService.getFilesByUser(user);

        log.info("파일 조회가 완료되었습니다.");
        log.debug("조회된 파일 개수 : {}개", files.size());

        // Convert File to File Dto here
        return files.stream()
                .map(FileResponseDto::new)
                .toList();
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "파일 업로드 완료", content = @Content(schema = @Schema(implementation = String.class))),
    })
    @Operation(summary = "파일 업로드 API", description = "사용자가 관리를 희망하는 파일을 업로드하는 API 입니다.")
    @PostMapping("/upload")
    public String addFile(@RequestBody MultipartFile file) {
        User user = userService.getUserById(SecurityUtils.getCurrentUserId());
        String uploadedFileUrl = s3FileService.uploadFile(user.getUserId(), file);
        fileService.addFile(user, uploadedFileUrl, file);

        log.info("파일 업로드가 완료되었습니다.");
        log.debug("업로드 파일 Url => {}", uploadedFileUrl);

        return "ok";
    }


    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "파일 다운로드 링크 전달 완료", content = @Content(schema = @Schema(implementation = DownloadFileResponseDto.class))),
    })
    @Operation(summary = "파일 다운로드 API", description = "파일의 다운로드 링크를 전달하는 API 입니다.")
    @PostMapping("/download")
    public DownloadFileResponseDto downloadFile(@RequestBody DownloadFileRequestDto downloadFileRequestDto) {
        File file = fileService.getFile(downloadFileRequestDto.getFileId());

        log.info("파일 다운로드 링크가 조회되었습니다.");
        log.debug("파일 다운로드 링크 => {}", file.getS3Url());

        return DownloadFileResponseDto.builder()
                .fileS3Url(file.getS3Url())
                .build();
    }


    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "파일 삭제 완료", content = @Content(schema = @Schema(implementation = String.class))),
    })
    @Operation(summary = "파일 삭제 API", description = "사용자가 업로드한 파일을 삭제하는 API 입니다.")
    @DeleteMapping("/remove")
    public String removeFile(@RequestBody RemoveFileRequestDto removeFileRequestDto) {
        File removeFile = fileService.getFile(removeFileRequestDto.getFileId());

        log.info("삭제 예정 파일이 조회되었습니다.");
        log.debug("삭제 예정 파일명 => {}", removeFile.getOriginalFileName());

        s3FileService.removeFileFromS3(removeFile.getS3Url());
        fileService.removeFile(removeFile);

        log.info("파일 삭제가 완료되었습니다.");

        return "ok";
    }
}
