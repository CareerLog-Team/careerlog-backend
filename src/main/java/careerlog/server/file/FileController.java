package careerlog.server.file;


import careerlog.server.config.security.SecurityUtils;
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
    public List<FileResponseDto> getFiles() {
        User user = userService.getUserById(SecurityUtils.getCurrentUserId());
        List<File> files = fileService.getFilesByUser(user);

        // Convert File to File Dto here
        return files.stream()
                .map(FileResponseDto::new)
                .toList();
    }

    @PostMapping
    public String addFile(@RequestBody MultipartFile file) {
        User user = userService.getUserById(SecurityUtils.getCurrentUserId());
        String uploadedFileUrl = s3FileService.uploadFile(user.getUserId(), file);
        fileService.addFile(user, uploadedFileUrl, file);

        return "ok";
    }

    @PostMapping("/download")
    public DownloadFileResponseDto downloadFile(@RequestBody DownloadFileRequestDto downloadFileRequestDto) {
        File file = fileService.getFile(downloadFileRequestDto.getFileId());

        return DownloadFileResponseDto.builder()
                .fileS3Url(file.getS3Url())
                .build();
    }

    @DeleteMapping
    public String removeFile(@RequestBody RemoveFileRequestDto removeFileRequestDto) {
        File removeFile = fileService.getFile(removeFileRequestDto.getFileId());

        s3FileService.removeFileFromS3(removeFile.getS3Url());
        fileService.removeFile(removeFile);

        return "ok";
    }
}
