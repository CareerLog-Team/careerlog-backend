package careerlog.server.careerboard.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Link{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String linkId;

    /*
    제목
     */
    private String title;

    /*
    링크
     */
    private String link;

    @Setter(AccessLevel.PROTECTED)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "careerBoardId")
    private CareerBoard careerBoard;

    @Builder
    public Link(String linkId, String title, String link) {
        this.linkId = linkId;
        this.title = title;
        this.link = link;
    }

    public void updateLink(Link updateLink) {
        this.title = updateLink.getTitle();
        this.link = updateLink.getLink();
    }
}
