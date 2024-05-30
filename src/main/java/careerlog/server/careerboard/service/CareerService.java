package careerlog.server.careerboard.service;


import careerlog.server.careerboard.domain.Career;
import careerlog.server.careerboard.repository.CareerRepository;
import careerlog.server.common.response.exception.CustomException;
import careerlog.server.common.response.resultcode.ResultCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CareerService {

    private final CareerRepository careerRepository;

    public Career getCareerById(String careerId) {
        return careerRepository.findById(careerId)
                .orElseThrow(() -> new CustomException(ResultCode.CAREER_NOT_FOUND_EXCEPTION));
    }

    @Transactional
    public void removeCareer(String removeCareerId) {
        careerRepository
                .findById(removeCareerId)
                .ifPresent(careerRepository::delete);
    }
}
