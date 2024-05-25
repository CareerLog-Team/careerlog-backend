package careerlog.server.careerboard.legacy.controller;


import careerlog.server.common.dto.ResponseDto;
import careerlog.server.common.response.resultcode.ResultCode;
import careerlog.server.careerboard.domain.Link;
import careerlog.server.careerboard.dto.request.add.AddLinkRequestDto;
import careerlog.server.careerboard.dto.request.remove.RemoveLinkRequestDto;
import careerlog.server.careerboard.dto.request.update.UpdateLinkRequestDto;
import careerlog.server.careerboard.dto.response.LinkResponseDto;
import careerlog.server.careerboard.service.LinkService;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Hidden
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v0/link")
public class LinkController {

    private final LinkService linkService;

    @GetMapping
    public ResponseDto<List<LinkResponseDto>> getlinks() {

        List<LinkResponseDto> links = linkService.getLinks();

        return new ResponseDto<>(
                ResultCode.SUCCESS.getCode(),
                links,
                ResultCode.SUCCESS.getMessage()
        );
    }

    @PostMapping
    public ResponseDto<?> addLinks(@RequestBody List<AddLinkRequestDto> addLinkRequestDtos) {
        List<Link> listWithoutId = addLinkRequestDtos.stream()
                .map(AddLinkRequestDto::toLinkWithoutId)
                .toList();

        linkService.addLinks(listWithoutId);

        return new ResponseDto<>(
                ResultCode.SUCCESS.getCode(),
                ResultCode.SUCCESS.getMessage()
        );
    }

    @PutMapping
    public ResponseDto<?> updateLinks(@RequestBody List<UpdateLinkRequestDto> updateLinkRequestDtos) {
        List<Link> linksWithId = updateLinkRequestDtos.stream()
                .map(UpdateLinkRequestDto::toLinkWithId)
                .toList();

        linkService.updateLinks(linksWithId);

        return new ResponseDto<>(
                ResultCode.SUCCESS.getCode(),
                ResultCode.SUCCESS.getMessage()
        );
    }

    @DeleteMapping
    public ResponseDto<?> removeLink(@RequestBody RemoveLinkRequestDto removeLinkRequestDto) {

        linkService.removeLink(removeLinkRequestDto.getLinkId());

        return new ResponseDto<>(
                ResultCode.SUCCESS.getCode(),
                ResultCode.SUCCESS.getMessage()
        );
    }
}
