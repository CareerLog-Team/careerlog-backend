package careerlog.server.file.domain;


import lombok.Getter;

@Getter
public enum ContentType {
    TEXT_PLAIN("txt", "text/plain"),
    TEXT_HTML("html", "text/html"),
    TEXT_CSS("css", "text/css"),
    APPLICATION_JAVASCRIPT("js", "application/javascript"),
    APPLICATION_JSON("json", "application/json"),
    APPLICATION_XML("xml", "application/xml"),
    IMAGE_JPEG("jpg", "image/jpeg"),
    IMAGE_PNG("png", "image/png"),
    IMAGE_GIF("gif", "image/gif"),
    APPLICATION_PDF("pdf", "application/pdf"),
    APPLICATION_OCTET_STREAM("", "application/octet-stream"); // 기본값

    private final String extension;
    private final String contentType;

    ContentType(String extension, String contentType) {
        this.extension = extension;
        this.contentType = contentType;
    }

    public static ContentType getByExtension(String extension) {
        for (ContentType type : values()) {
            if (type.getExtension().equalsIgnoreCase(extension)) {
                return type;
            }
        }
        return APPLICATION_OCTET_STREAM; // 확장자에 해당하는 Content-Type이 없을 경우 기본값 반환
    }
}