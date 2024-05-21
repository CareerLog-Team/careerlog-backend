package careerlog.server.file.dto;


import careerlog.server.file.domain.File;
import careerlog.server.utils.LocalDateUtils;
import lombok.Data;

import java.time.LocalDate;

@Data
public class FileResponseDto {
    private String fileId;
    private String originalFileName;
    private String extension;
    private String fileSize;
    private String createDt;

    public FileResponseDto(File file) {
        this.fileId = file.getFileId();
        this.originalFileName = file.getOriginalFileName();
        this.extension = file.getExtension();
        this.fileSize = file.getFileSize();
        this.createDt = LocalDateUtils.formatDate(LocalDate.from(file.getCreateDt()));
    }
}
