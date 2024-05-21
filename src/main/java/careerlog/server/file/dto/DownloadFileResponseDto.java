package careerlog.server.file.dto;


import lombok.Builder;
import lombok.Data;

@Data
public class DownloadFileResponseDto {

    private String fileS3Url;

    @Builder
    public DownloadFileResponseDto(String fileS3Url) {
        this.fileS3Url = fileS3Url;
    }
}
