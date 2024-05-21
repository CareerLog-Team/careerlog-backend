package careerlog.server.careerboard.dto.request.update;


import careerlog.server.careerboard.domain.Link;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class UpdateLinkRequestDto {

    private String linkId;
    private String title;
    private String link;

    public Link toLinkWithId() {
        return Link.builder()
                .linkId(this.getLinkId())
                .title(this.getTitle())
                .link(this.getLink())
                .build();
    }
}
