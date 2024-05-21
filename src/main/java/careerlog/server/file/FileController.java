package careerlog.server.file;


import careerlog.server.common.dto.ResponseDto;
import careerlog.server.common.resultcode.ResultCode;
import careerlog.server.config.SecurityUtils;
import careerlog.server.file.domain.File;
import careerlog.server.file.dto.*;
import careerlog.server.file.service.FileService;
import careerlog.server.file.service.S3FileService;
import careerlog.server.user.domain.User;
import careerlog.server.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v0/file")
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;
    private final S3FileService s3FileService;
    private final UserService userService;

    @GetMapping
    public ResponseDto<List<FileResponseDto>> getFiles() {
        User user = userService.getUserById(SecurityUtils.getCurrentUserId());
        List<File> files = fileService.getFilesByUser(user);

        // Convert File to File Dto here
        List<FileResponseDto> fileResponseDtos = files.stream()
                .map(FileResponseDto::new)
                .toList();

        return new ResponseDto<>(
                ResultCode.SUCCESS.getCode(),
                fileResponseDtos,
                ResultCode.SUCCESS.getMessage()
        );
    }

    @PostMapping
    public ResponseDto<Void> addFile(@RequestBody MultipartFile file) {
        User user = userService.getUserById(SecurityUtils.getCurrentUserId());
        String uploadedFileUrl = s3FileService.uploadFile(user.getUserId(), file);
        fileService.addFile(user, uploadedFileUrl, file);

        return new ResponseDto<>(
                ResultCode.SUCCESS.getCode(),
                ResultCode.SUCCESS.getMessage()
        );
    }

    @PostMapping("/download")
    public ResponseDto<DownloadFileResponseDto> downloadFile(@RequestBody DownloadFileRequestDto downloadFileRequestDto) {
        File file = fileService.getFile(downloadFileRequestDto.getFileId());

        DownloadFileResponseDto downloadFileResponseDto = DownloadFileResponseDto.builder()
                .fileS3Url(file.getS3Url())
                .build();

        return new ResponseDto<>(
                ResultCode.SUCCESS.getCode(),
                downloadFileResponseDto,
                ResultCode.SUCCESS.getMessage()
        );
    }

    @DeleteMapping
    public ResponseDto<Void> removeFile(@RequestBody RemoveFileRequestDto removeFileRequestDto) {
        File removeFile = fileService.getFile(removeFileRequestDto.getFileId());

        s3FileService.removeFileFromS3(removeFile.getS3Url());
        fileService.removeFile(removeFile);

        return new ResponseDto<>(
                ResultCode.SUCCESS.getCode(),
                ResultCode.SUCCESS.getMessage()
        );
    }
}
