package careerlog.server.file.service;


import careerlog.server.common.response.exception.CustomException;
import careerlog.server.common.response.resultcode.ResultCode;
import careerlog.server.file.domain.ContentType;
import careerlog.server.utils.FileUtils;
import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.util.IOUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class S3FileService {

    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    // 파일 저장 기본 경로 : {user-UUID}/{randomUUID}-{originalFileName}.{확장자}

    public String uploadFile(String userId, MultipartFile file) {
        try {
            return uploadFileToS3(userId, file);
        } catch (IOException e) {
            log.warn(e.getMessage());
            throw new CustomException(ResultCode.INTERNAL_SERVER_ERROR);
        }
    }

    private String uploadFileToS3(String userId, MultipartFile file) throws IOException {
        // DB에는 원래 파일명, 저장된 URL, 파일 확장자, 파일 크기, 업로드 날짜가 포함되어야 함

        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null) {
            // NO_FILE_EXCEPTION
            throw new CustomException(ResultCode.INTERNAL_SERVER_ERROR);
        }

        String ext = FileUtils.getFileExtension(originalFilename);
        String contentType = ContentType.getByExtension(ext).getContentType();

        String s3FileName = userId + "/" + UUID.randomUUID() + "." + ext;

        InputStream inputStream = file.getInputStream();
        byte[] bytes = IOUtils.toByteArray(inputStream);

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(contentType);
        metadata.setContentLength(bytes.length);
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);

        try {
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucket, s3FileName, byteArrayInputStream, metadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead);

            amazonS3Client.putObject(putObjectRequest);
        }
        catch (Exception e) {
            // PUT_OBJECT_EXCEPTION
            log.warn(e.getMessage());
            throw new CustomException(ResultCode.INTERNAL_SERVER_ERROR);
        }
        finally {
            byteArrayInputStream.close();
            inputStream.close();
        }

        return amazonS3Client.getUrl(bucket, s3FileName).toString();
    }

    public void removeFileFromS3(String fileUrl) {
        String key = getKeyFromFileUrl(fileUrl);

        try {
            amazonS3Client.deleteObject(new DeleteObjectRequest(bucket, key));
        } catch (SdkClientException e) {
            log.warn(e.getMessage());
            throw new CustomException(ResultCode.INTERNAL_SERVER_ERROR);
        }
    }

    private String getKeyFromFileUrl(String fileUrl) {
        try {
            URL url = new URL(fileUrl);
            String decodedKey = URLDecoder.decode(url.getPath(), StandardCharsets.UTF_8);
            return decodedKey.substring(1);
        } catch (MalformedURLException e) {
            // TODO : S3 Exception No File to Remove 로 처리
            log.warn(e.getMessage());
            throw new CustomException(ResultCode.INTERNAL_SERVER_ERROR);
        }
    }
}
