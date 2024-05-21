package careerlog.server.careerboard.dto.request.save;


import careerlog.server.careerboard.dto.request.update.UpdateCareerRequestDto;
import careerlog.server.careerboard.dto.request.add.AddCareerRequestDto;
import careerlog.server.careerboard.dto.request.remove.RemoveCareerRequestDto;
import lombok.Data;
import lombok.Getter;

import java.util.List;

@Data
@Getter
public class SaveCareerRequestDto {

    private List<RemoveCareerRequestDto> removeCareerRequestDtos;
    private List<UpdateCareerRequestDto> updateCareerRequestDtos;
    private List<AddCareerRequestDto> addCareerRequestDtos;

}
