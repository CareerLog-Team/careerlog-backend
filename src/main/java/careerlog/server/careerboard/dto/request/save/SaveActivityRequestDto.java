package careerlog.server.careerboard.dto.request.save;


import careerlog.server.careerboard.dto.request.update.UpdateActivityRequestDto;
import careerlog.server.careerboard.dto.request.add.AddActivityRequestDto;
import careerlog.server.careerboard.dto.request.remove.RemoveActivityRequestDto;
import lombok.Data;
import lombok.Getter;

import java.util.List;

@Data
@Getter
public class SaveActivityRequestDto {
    private List<RemoveActivityRequestDto> removeActivityRequestDtos;
    private List<UpdateActivityRequestDto> updateActivityRequestDtos;
    private List<AddActivityRequestDto> addActivityRequestDtos;
}
