package careerlog.server.file.service;


import careerlog.server.common.exception.CustomException;
import careerlog.server.common.resultcode.ResultCode;
import careerlog.server.file.domain.File;
import careerlog.server.file.repository.FileRepository;
import careerlog.server.user.domain.User;
import careerlog.server.utils.FileUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FileService {

    private final FileRepository fileRepository;

    public File getFile(String fileId) {
        return fileRepository.findById(fileId)
                .orElseThrow(() -> new CustomException(ResultCode.INTERNAL_SERVER_ERROR));
    }

    public List<File> getFilesByUser(User user) {
        return fileRepository.findFilesByUser(user);
    }


    @Transactional
    public void addFile(User user, String uploadedFileUrl, MultipartFile file) {
        String originalFilename = file.getOriginalFilename();

        if (originalFilename == null) {
            // NO_FILE_EXCEPTION
            throw new CustomException(ResultCode.INTERNAL_SERVER_ERROR);
        }

        String ext = FileUtils.getFileExtension(originalFilename);
        String fileSize = FileUtils.convertBytes(file.getSize());

        File addFile = File.builder()
                .originalFileName(originalFilename)
                .s3Url(uploadedFileUrl)
                .fileSize(fileSize)
                .extension(ext)
                .user(user)
                .build();

        fileRepository.save(addFile);
    }

    @Transactional
    public void removeFile(File removeFile) {
        fileRepository.delete(removeFile);
    }
}
