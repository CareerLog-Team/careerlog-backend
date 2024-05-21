package careerlog.server.careerboard.service;


import careerlog.server.careerboard.domain.Career;
import careerlog.server.careerboard.dto.response.CareerResponseDto;
import careerlog.server.careerboard.repository.CareerRepository;
import careerlog.server.common.exception.CustomException;
import careerlog.server.common.resultcode.ResultCode;
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
public class CareerService {

    private final CareerRepository careerRepository;

    public List<CareerResponseDto> getCareers() {
        // findAll => findAllById로 변경 필요 (일단 임시로 구성)
        List<Career> careers = careerRepository.findAll();
        return toCareerResponseDtos(careers);
    }

    public Career getCareer(String careerId) {
        Optional<Career> _career = careerRepository.findById(careerId);
        if (_career.isEmpty()) {
            throw new CustomException(ResultCode.CAREER_NOT_FOUND_EXCEPTION);
        }
        return _career.get();
    }

    @Transactional
    public void addCareers(List<Career> careersWithoutId) {
        List<Career> savedCareers = careerRepository.saveAll(careersWithoutId);

        for (Career savedCareer : savedCareers) {
            log.info("savedCareer -> {}", savedCareer.toString());
        }
    }

    @Transactional
    public void updateCareers(List<Career> careersWithId) {
        List<Career> findCareers = careerRepository.findAll();
        Map<String, Career> careersWithIdMap = getCareersWithIdMap(careersWithId);

        for (Career findCareer : findCareers) {
            Career updateCareer = careersWithIdMap.get(findCareer.getCareerId());
            findCareer.updateCareer(updateCareer);
        }
    }

    @Transactional
    public void removeCareer(String removeCareerId) {
        Optional<Career> _removeCareer = careerRepository.findById(removeCareerId);

        // 이미 없어도 에러는 아니니 정상 처리 로직만 구현 / 없을 때 로직은 나중에 필요하면 활용
        _removeCareer.ifPresent(careerRepository::delete);
    }


    private List<CareerResponseDto> toCareerResponseDtos(List<Career> careers) {
        return careers.stream()
                .map(CareerResponseDto::new)
                .collect(Collectors.toList());
    }

    private Map<String, Career> getCareersWithIdMap(List<Career> careersWithId) {
        return careersWithId.stream()
                .collect(Collectors.toMap(Career::getCareerId, career -> career));
    }

}
