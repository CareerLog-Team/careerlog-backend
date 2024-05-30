package careerlog.server.careerboard.service;


import careerlog.server.careerboard.repository.LinkRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class LinkService {

    private final LinkRepository linkRepository;

    @Transactional
    public void removeLink(String removeLinkId) {
        linkRepository
                .findById(removeLinkId)
                .ifPresent(linkRepository::delete);
    }

}
