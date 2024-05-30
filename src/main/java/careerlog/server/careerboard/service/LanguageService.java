package careerlog.server.careerboard.service;


import careerlog.server.careerboard.repository.LanguageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class LanguageService {

    private final LanguageRepository languageRepository;

    @Transactional
    public void removeLanguage(String removeLanguageId) {
        languageRepository
                .findById(removeLanguageId)
                .ifPresent(languageRepository::delete);
    }
}
