package careerlog.server.careerboard.service;


import careerlog.server.careerboard.domain.*;
import careerlog.server.careerboard.dto.request.add.*;
import careerlog.server.careerboard.dto.request.remove.*;
import careerlog.server.careerboard.dto.request.save.SaveAutoRequestDto;
import careerlog.server.careerboard.dto.request.update.*;
import careerlog.server.careerboard.repository.CareerBoardRepository;
import careerlog.server.common.response.exception.CustomException;
import careerlog.server.common.response.resultcode.ResultCode;
import careerlog.server.user.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class CareerBoardService {
    private final CareerBoardRepository careerBoardRepository;

    private final ActivityService activityService;
    private final CertificateService certificateService;
    private final CareerService careerService;
    private final EducationService educationService;
    private final LanguageService languageService;
    private final LinkService linkService;


    public CareerBoard getCareerBoardByUser(User user) {
        return careerBoardRepository.findCareerBoardByUser(user)
                .orElseThrow(() -> new CustomException(ResultCode.CAREER_BOARD_NOT_FOUND_EXCEPTION));
    }

    // save 로직 작성 필요
//    public void saveActivityProcess(List<Activity> activities, List<Activity> newActivities) {
//        // 1. 기존 activity Map 으로 변환
//        Map<String, Activity> existingActivities = activities
//                .stream()
//                .collect(Collectors.toMap(Activity::getActivityId, activity -> activity));
//
//        // 2. 새롭게 전달받은 activity Map으로 변환
//        // 수정된 항목 => Id를 보유하고 있을 것 / 제거된 항목 => 어차피 전달되는 항목에 포함되지 않을 것 / 추가된 항목 => Id가 없을 것
//        Map<String, Activity> newExistingActivities = newActivities
//                .stream()
//                .collect(Collectors.toMap(Activity::getActivityId, activity -> activity));
//
//        // 3. 전달받은 항목에서 기존 항목이 없을경우 제거
//        for (String id : existingActivities.keySet()) {
//            if (!newExistingActivities.containsKey(id)) {
//                activityService.removeActivityById(id);
//            }
//        }
//
//        // 4. 새롭게 받은 항목들을 조회
//        for (Activity newActivity : newActivities) {
//
//        }
//
//    }

    @Transactional
    public void saveCareer(
            CareerBoard careerBoard,
            List<RemoveCareerRequestDto> removeCareerRequestDtos,
            List<UpdateCareerRequestDto> updateCareerRequestDtos,
            List<AddCareerRequestDto> addCareerRequestDtos
    ) {
        // 1. 삭제 로직
        for (RemoveCareerRequestDto removeCareerRequestDto : removeCareerRequestDtos) {
            careerService.removeCareer(removeCareerRequestDto.getCareerId());
        }

        // 2. 업데이트 로직
        List<Career> careersWithId = updateCareerRequestDtos.stream()
                .map(UpdateCareerRequestDto::toCareerEntityWithId)
                .toList();

        careerService.updateCareers(careersWithId);

        // 3. 추가 로직
        addCareerRequestDtos.stream()
                .map(AddCareerRequestDto::toCareerEntityWithoutId)
                .forEach(careerBoard::addCareer);
    }

    @Transactional
    public void saveEducation(
            CareerBoard careerBoard,
            List<RemoveEducationRequestDto> removeEducationRequestDtos,
            List<UpdateEducationRequestDto> updateEducationRequestDtos,
            List<AddEducationRequestDto> addEducationRequestDtos
    ) {
        // 1. 삭제 로직
        for (RemoveEducationRequestDto removeEducationRequestDto : removeEducationRequestDtos) {
            educationService.removeEducation(removeEducationRequestDto.getEducationId());
        }


        // 2. 업데이트 로직
        List<Education> educationsWithId = updateEducationRequestDtos.stream()
                .map(UpdateEducationRequestDto::toEducationWithId)
                .toList();

        educationService.updateEducations(educationsWithId);

        // 3. 추가 로직
        addEducationRequestDtos.stream()
                .map(AddEducationRequestDto::toEducationWithoutId)
                .forEach(careerBoard::addEducation);
    }

    @Transactional
    public void saveCertificate(
            CareerBoard careerBoard,
            List<RemoveCertificateRequestDto> removeCertificateRequestDtos,
            List<UpdateCertificateRequestDto> updateCertificateRequestDtos,
            List<AddCertificateRequestDto> addCertificateRequestDtos
    ) {
        // 1. 삭제 로직
        for (RemoveCertificateRequestDto removeCertificateRequestDto : removeCertificateRequestDtos) {
            certificateService.removeCertificate(removeCertificateRequestDto.getCertificateId());
        }

        // 2. 수정 로직
        List<Certificate> certificatesWithId = updateCertificateRequestDtos.stream()
                .map(UpdateCertificateRequestDto::toCertificateWithId)
                .toList();

        certificateService.updateCertificates(certificatesWithId);

        // 3. 추가 로직
        addCertificateRequestDtos.stream()
                .map(AddCertificateRequestDto::toCertificateWithoutId)
                .forEach(careerBoard::addCertificate);
    }

    @Transactional
    public void saveActivity(
            CareerBoard careerBoard,
            List<RemoveActivityRequestDto> removeActivityRequestDtos,
            List<UpdateActivityRequestDto> updateActivityRequestDtos,
            List<AddActivityRequestDto> addActivityRequestDtos
    ) {
        for (RemoveActivityRequestDto removeActivityRequestDto : removeActivityRequestDtos) {
            activityService.removeActivityById(removeActivityRequestDto.getActivityId());
        }

        List<Activity> activitiesWithId = updateActivityRequestDtos.stream()
                .map(UpdateActivityRequestDto::toActivityWithId)
                .toList();
        activityService.updateActivities(activitiesWithId);

        addActivityRequestDtos.stream()
                .map(AddActivityRequestDto::toActivityWithoutId)
                .forEach(careerBoard::addActivity);
    }

    @Transactional
    public void saveAuto(
            CareerBoard careerBoard,
            SaveAutoRequestDto saveAutoRequestDto
    ) {
        saveLanguage(
                careerBoard,
                saveAutoRequestDto.getSaveLanguageRequestDto().getRemoveLanguageRequestDtos(),
                saveAutoRequestDto.getSaveLanguageRequestDto().getUpdateLanguageRequestDtos(),
                saveAutoRequestDto.getSaveLanguageRequestDto().getAddLanguageRequestDtos()
        );

        saveLink(
                careerBoard,
                saveAutoRequestDto.getSaveLinkRequestDto().getRemoveLinkRequestDtos(),
                saveAutoRequestDto.getSaveLinkRequestDto().getUpdateLinkRequestDtos(),
                saveAutoRequestDto.getSaveLinkRequestDto().getAddLinkRequestDtos()
        );

        saveSkills(
                careerBoard,
                saveAutoRequestDto.getSkills()
        );
    }

    @Transactional // Transactional을 달고있는 Method는 private를 할 수 없음
    protected void saveLanguage(
            CareerBoard careerBoard,
            List<RemoveLanguageRequestDto> removeLanguageRequestDtos,
            List<UpdateLanguageRequestDto> updateLanguageRequestDtos,
            List<AddLanguageRequestDto> addLanguageRequestDtos
    ) {
        for (RemoveLanguageRequestDto removeLanguageRequestDto : removeLanguageRequestDtos) {
            languageService.removeLanguage(removeLanguageRequestDto.getLanguageId());
        }

        List<Language> languagesWithId = updateLanguageRequestDtos.stream()
                .map(UpdateLanguageRequestDto::toLanguageWithId)
                .toList();

        languageService.updateLanguages(languagesWithId);

        addLanguageRequestDtos.stream()
                .map(AddLanguageRequestDto::toLanguageWithoutId)
                .forEach(careerBoard::addLanguage);
    }

    @Transactional
    protected void saveLink (
            CareerBoard careerBoard,
            List<RemoveLinkRequestDto> removeLinkRequestDtos,
            List<UpdateLinkRequestDto> updateLinkRequestDtos,
            List<AddLinkRequestDto> addLinkRequestDtos
    ) {
        for (RemoveLinkRequestDto removeLinkRequestDto : removeLinkRequestDtos) {
            languageService.removeLanguage(removeLinkRequestDto.getLinkId());
        }

        List<Link> linksWithId = updateLinkRequestDtos.stream()
                .map(UpdateLinkRequestDto::toLinkWithId)
                .toList();

        linkService.updateLinks(linksWithId);

        addLinkRequestDtos.stream()
                .map(AddLinkRequestDto::toLinkWithoutId)
                .forEach(careerBoard::addLink);
    }

    @Transactional
    protected void saveSkills(CareerBoard careerBoard, List<String> links) {
        careerBoard.setSkills(links);
    }
}
