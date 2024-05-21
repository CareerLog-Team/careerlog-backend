package careerlog.server.resume.dto;


import careerlog.server.resume.domain.ItemType;
import careerlog.server.resume.domain.ResumeItem;
import lombok.Data;

import java.util.List;

@Data
public class CreateResumeRequestDto {

    private String resumeTypeCode;
    private List<ResumeItemDto> resumeItemDtos;

    @Data
    public static class ResumeItemDto {
        private String resumeItemType;
        private String resumeItemId;
        private Boolean isTrue;

        public ResumeItem toResumeItem() {
            return ResumeItem.builder()
                    .itemType(ItemType.fromValue(this.getResumeItemType()))
                    .resumeItemId(this.getResumeItemId())
                    .isTrue(this.getIsTrue())
                    .build();
        }
    }
}

