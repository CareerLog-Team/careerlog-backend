package careerlog.server.careerboard.service;


import careerlog.server.careerboard.domain.Link;
import careerlog.server.careerboard.dto.response.LinkResponseDto;
import careerlog.server.careerboard.repository.LinkRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class LinkService {

    private final LinkRepository linkRepository;

    public List<LinkResponseDto> getLinks() {
        List<Link> links = linkRepository.findAll();
        return toLinkResponseDtos(links);
    }

    @Transactional
    public void addLinks(List<Link> linksWithoutId) {
        List<Link> savedLinks = linkRepository.saveAll(linksWithoutId);
        for (Link savedLink : savedLinks) {
            log.info("savedLink => {}", savedLink.toString());
        }
    }

    @Transactional
    public void updateLinks(List<Link> linksWithId) {
        List<Link> findLinks = linkRepository.findAll();
        Map<String, Link> linksWithIdMap = getLinksWithIdMap(linksWithId);

        for (Link findLink : findLinks) {
            Link updateLink = linksWithIdMap.get(findLink.getLinkId());
            findLink.updateLink(updateLink);
        }
    }

    @Transactional
    public void removeLink(String removeLinkId) {
        Optional<Link> _removeLink = linkRepository.findById(removeLinkId);
        _removeLink.ifPresent(linkRepository::delete);
    }

    private Map<String, Link> getLinksWithIdMap(List<Link> linksWithId) {
        return linksWithId.stream()
                .collect(Collectors.toMap(Link::getLink, link -> link));
    }

    private List<LinkResponseDto> toLinkResponseDtos(List<Link> links) {
        return links.stream()
                .map(LinkResponseDto::new)
                .toList();
    }

}
