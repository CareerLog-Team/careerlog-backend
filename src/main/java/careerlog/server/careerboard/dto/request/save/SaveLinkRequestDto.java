package careerlog.server.careerboard.dto.request.save;


import careerlog.server.careerboard.dto.request.add.AddLinkRequestDto;
import careerlog.server.careerboard.dto.request.update.UpdateLinkRequestDto;
import careerlog.server.careerboard.dto.request.remove.RemoveLinkRequestDto;
import lombok.Data;
import lombok.Getter;

import java.util.List;

@Data
@Getter
public class SaveLinkRequestDto {

    private List<RemoveLinkRequestDto> removeLinkRequestDtos;
    private List<UpdateLinkRequestDto> updateLinkRequestDtos;
    private List<AddLinkRequestDto> addLinkRequestDtos;

}
