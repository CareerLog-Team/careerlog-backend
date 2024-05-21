package careerlog.server.careerboard.dto.request.add;


import careerlog.server.careerboard.domain.Link;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class AddLinkRequestDto {

    private String title;
    private String link;
    
    public Link toLinkWithoutId() {
        return Link.builder()
                .title(this.getTitle())
                .link(this.getLink())
                .build();
    }
}
