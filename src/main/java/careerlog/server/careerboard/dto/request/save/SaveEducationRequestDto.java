package careerlog.server.careerboard.dto.request.save;


import careerlog.server.careerboard.dto.request.update.UpdateEducationRequestDto;
import careerlog.server.careerboard.dto.request.add.AddEducationRequestDto;
import careerlog.server.careerboard.dto.request.remove.RemoveEducationRequestDto;
import lombok.Data;
import lombok.Getter;

import java.util.List;

@Data
@Getter
public class SaveEducationRequestDto {

    private List<RemoveEducationRequestDto> removeEducationRequestDtos;
    private List<UpdateEducationRequestDto> updateEducationRequestDtos;
    private List<AddEducationRequestDto> addEducationRequestDtos;

}
