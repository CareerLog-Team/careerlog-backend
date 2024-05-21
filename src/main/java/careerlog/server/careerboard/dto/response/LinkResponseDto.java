package careerlog.server.careerboard.dto.response;


import careerlog.server.careerboard.domain.Link;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class LinkResponseDto {

    private String linkId;
    private String title;
    private String link;

    public LinkResponseDto(Link link) {
        this.linkId = link.getLinkId();
        this.title = link.getTitle();
        this.link = link.getLink();
    }
}
