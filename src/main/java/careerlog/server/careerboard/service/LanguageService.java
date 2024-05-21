package careerlog.server.careerboard.service;


import careerlog.server.careerboard.domain.Language;
import careerlog.server.careerboard.dto.response.LanguageResponseDto;
import careerlog.server.careerboard.repository.LanguageRepository;
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
public class LanguageService {

    private final LanguageRepository languageRepository;

    public List<LanguageResponseDto> getLanguages() {
        List<Language> languages = languageRepository.findAll();
        return toLanguageResponseDtos(languages);
    }

    @Transactional
    public void addLanguages(List<Language> languagesWithoutId) {
        List<Language> savedLanguages = languageRepository.saveAll(languagesWithoutId);

        for (Language savedLanguage : savedLanguages) {
            log.info("savedLanguage => {}", savedLanguage.toString());
        }
    }

    @Transactional
    public void updateLanguages(List<Language> languagesWithId) {
        List<Language> findLanguages = languageRepository.findAll();
        Map<String, Language> languageWithIdMap = getLanguageWithIdMap(languagesWithId);

        for (Language findLanguage : findLanguages) {
            Language updateLanguage = languageWithIdMap.get(findLanguage.getLanguageId());
            findLanguage.updateLanguage(updateLanguage);
        }
    }

    @Transactional
    public void removeLanguage(String removeLanguageId) {
        Optional<Language> _removeLanguage = languageRepository.findById(removeLanguageId);
        _removeLanguage.ifPresent(languageRepository::delete);
    }

    private List<LanguageResponseDto> toLanguageResponseDtos(List<Language> languages) {
        return languages.stream()
                .map(LanguageResponseDto::new)
                .toList();
    }

    private Map<String, Language> getLanguageWithIdMap(List<Language> languagesWithId) {
        return languagesWithId.stream()
                .collect(Collectors.toMap(Language::getLanguageId, language -> language));
    }
}
