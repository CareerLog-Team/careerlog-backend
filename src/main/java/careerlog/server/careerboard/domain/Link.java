package careerlog.server.careerboard.domain;

import careerlog.server.common.response.exception.CustomException;
import careerlog.server.common.response.resultcode.ResultCode;
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

    public void setCareerBoard(CareerBoard careerBoard) {
        if (this.careerBoard != null) {
            // 이미 CareerBoard가 세팅되어있다는 Exception을 반환
            throw new CustomException(ResultCode.INTERNAL_SERVER_ERROR);
        }

        this.careerBoard = careerBoard;
    }
}
