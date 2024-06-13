package careerlog.server.careerboard.service;


import careerlog.server.careerboard.domain.*;
import careerlog.server.careerboard.dto.request.add.*;
import careerlog.server.careerboard.dto.request.remove.*;
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
import java.util.Map;
import java.util.stream.Collectors;

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

    public void addCareerBoard(User user) {
        CareerBoard careerBoard = CareerBoard.createCareerBoard("", user);
        careerBoardRepository.save(careerBoard);
    }

    /* save 로직 작성 필요
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
//    }

// Sample Before Code
    @Transactional
    public void saveCareer(
            CareerBoard careerBoard,
            List<String> removeCareerIds,
            List<Career> updateCareers,
            List<Career> addCareers
    ) {
        for (String removeCareerId : removeCareerIds) {
            careerService.removeCareer(removeCareerId);
        }

        // 2. 업데이트
        List<Career> findCareers = careerBoard.getCareers();
        Map<String, Career> updateCareersMap = updateCareers.stream()
                .collect(Collectors.toMap(Career::getCareerId, career -> career));

        for (Career findCareer : findCareers) {
            findCareer.updateCareer(updateCareersMap.get(findCareer.getCareerId()));
        }

        // 3. 항목 추가
        for (Career addCareer : addCareers) {
            careerBoard.addCareer(addCareer);
        }
    }
     */


    @Transactional
    public void saveActivity(
            User user,
            List<RemoveActivityRequestDto> removeActivityRequestDtos,
            List<UpdateActivityRequestDto> updateActivityRequestDtos,
            List<AddActivityRequestDto> addActivityRequestDtos
    ) {
        CareerBoard careerBoard = getCareerBoardByUser(user);

        // 1. 제거
        List<String> removeActivityIds = removeActivityRequestDtos.stream()
                .map(RemoveActivityRequestDto::getActivityId)
                .toList();
        for (String removeActivityId : removeActivityIds) {
            activityService.removeActivityById(removeActivityId);
        }

        // 2. 업데이트
        List<Activity> updateActivities = updateActivityRequestDtos.stream()
                .map(UpdateActivityRequestDto::toActivityWithId)
                .toList();
        Map<String, Activity> updateActivitiesMap = updateActivities.stream()
                .collect(Collectors.toMap(Activity::getActivityId, activity -> activity));
        List<Activity> findActivities = careerBoard.getActivities();

        for (Activity findActivity : findActivities) {
            findActivity.updateActivity(updateActivitiesMap.get(findActivity.getActivityId()));
        }

        // 3. 항목 추가
        List<Activity> addActivities = addActivityRequestDtos.stream()
                .map(AddActivityRequestDto::toActivityWithoutId)
                .toList();

        for (Activity addActivity : addActivities) {
            careerBoard.addActivity(addActivity);
        }
    }



    @Transactional
    public void saveCareer(
            User user,
            List<RemoveCareerRequestDto> removeCareerRequestDtos,
            List<UpdateCareerRequestDto> updateCareerRequestDtos,
            List<AddCareerRequestDto> addCareerRequestDtos
    ) {
        CareerBoard careerBoard = getCareerBoardByUser(user);

        List<String> removeCareerIds = removeCareerRequestDtos.stream()
                .map(RemoveCareerRequestDto::getCareerId)
                .toList();

        for (String removeCareerId : removeCareerIds) {
            careerService.removeCareer(removeCareerId);
        }

        // 2. 업데이트
        List<Career> findCareers = careerBoard.getCareers();
        List<Career> updateCareers = updateCareerRequestDtos.stream()
                .map(UpdateCareerRequestDto::toCareerEntityWithId)
                .toList();

        Map<String, Career> updateCareersMap = updateCareers.stream()
                .collect(Collectors.toMap(Career::getCareerId, career -> career));

        List<Career> addCareers = addCareerRequestDtos.stream()
                .map(AddCareerRequestDto::toCareerEntityWithoutId)
                .toList();

        for (Career findCareer : findCareers) {
            findCareer.updateCareer(updateCareersMap.get(findCareer.getCareerId()));
        }

        // 3. 항목 추가
        for (Career addCareer : addCareers) {
            careerBoard.addCareer(addCareer);
        }
    }

    @Transactional
    public void saveCertificate(
            User user,
            List<RemoveCertificateRequestDto> removeCertificateRequestDtos,
            List<UpdateCertificateRequestDto> updateCertificateRequestDtos,
            List<AddCertificateRequestDto> addCertificateRequestDtos
    ) {
        CareerBoard careerBoard = getCareerBoardByUser(user);

        // 1. 삭제 로직
        List<String> removeCertificateIds = removeCertificateRequestDtos.stream()
                .map(RemoveCertificateRequestDto::getCertificateId)
                .toList();

        for (String removeCertificateId : removeCertificateIds) {
            certificateService.removeCertificate(removeCertificateId);
        }

        // 2. 수정 로직
        List<Certificate> findCertificates = careerBoard.getCertificates();
        List<Certificate> updateCertificates = updateCertificateRequestDtos.stream()
                .map(UpdateCertificateRequestDto::toCertificateWithId)
                .toList();

        Map<String, Certificate> updateCertificatesMap = updateCertificates.stream()
                .collect(Collectors.toMap(Certificate::getCertificateId, certificate -> certificate));

        for (Certificate findCertificate : findCertificates) {
            findCertificate.updateCertificate(updateCertificatesMap.get(findCertificate.getCertificateId()));
        }

        List<Certificate> addCertificates = addCertificateRequestDtos.stream()
                .map(AddCertificateRequestDto::toCertificateWithoutId)
                .toList();

        for (Certificate addCertificate : addCertificates) {
            careerBoard.addCertificate(addCertificate);
        }
    }

    @Transactional
    public void saveEducation(
            User user,
            List<RemoveEducationRequestDto> removeEducationRequestDtos,
            List<UpdateEducationRequestDto> updateEducationRequestDtos,
            List<AddEducationRequestDto> addEducationRequestDtos
    ) {
        CareerBoard careerBoard = getCareerBoardByUser(user);

        List<String> removeEducationIds = removeEducationRequestDtos.stream()
                .map(RemoveEducationRequestDto::getEducationId)
                .toList();

        // 1. 삭제 로직
        for (String removeEducationId : removeEducationIds) {
            educationService.removeEducation(removeEducationId);
        }

        List<Education> findEducations = careerBoard.getEducations();
        List<Education> updateEducations = updateEducationRequestDtos.stream()
                .map(UpdateEducationRequestDto::toEducationWithId)
                .toList();

        Map<String, Education> updateEducationsMap = updateEducations.stream()
                .collect(Collectors.toMap(Education::getEducationId, education -> education));

        for (Education findEducation : findEducations) {
            findEducation.updateEducation(updateEducationsMap.get(findEducation.getEducationId()));
        }

        List<Education> addEducations = addEducationRequestDtos.stream()
                .map(AddEducationRequestDto::toEducationWithoutId)
                .toList();

        for (Education addEducation : addEducations) {
            careerBoard.addEducation(addEducation);
        }
    }

    @Transactional // Transactional을 달고있는 Method는 private를 할 수 없음
    public void saveLanguage(
            User user,
            List<RemoveLanguageRequestDto> removeLanguageRequestDtos,
            List<UpdateLanguageRequestDto> updateLanguageRequestDtos,
            List<AddLanguageRequestDto> addLanguageRequestDtos
    ) {
        CareerBoard careerBoard = getCareerBoardByUser(user);

        List<String> removeLanguageIds = removeLanguageRequestDtos.stream()
                .map(RemoveLanguageRequestDto::getLanguageId)
                .toList();

        for (String removeLanguageId : removeLanguageIds) {
            languageService.removeLanguage(removeLanguageId);
        }

        List<Language> findLanguages = careerBoard.getLanguages();
        List<Language> updateLanguages = updateLanguageRequestDtos.stream()
                .map(UpdateLanguageRequestDto::toLanguageWithId)
                .toList();

        Map<String, Language> updateLanguagesMap = updateLanguages.stream()
                .collect(Collectors.toMap(Language::getLanguageId, language -> language));

        for (Language findLanguage : findLanguages) {
            findLanguage.updateLanguage(updateLanguagesMap.get(findLanguage.getLanguageId()));
        }

        List<Language> addLanguages = addLanguageRequestDtos.stream()
                .map(AddLanguageRequestDto::toLanguageWithoutId)
                .toList();

        for (Language addLanguage : addLanguages) {
            careerBoard.addLanguage(addLanguage);
        }
    }

    @Transactional
    public void saveLink (
            User user,
            List<RemoveLinkRequestDto> removeLinkRequestDtos,
            List<UpdateLinkRequestDto> updateLinkRequestDtos,
            List<AddLinkRequestDto> addLinkRequestDtos
    ) {
        CareerBoard careerBoard = getCareerBoardByUser(user);

        List<String> removeLinkIds = removeLinkRequestDtos.stream()
                .map(RemoveLinkRequestDto::getLinkId)
                .toList();

        for (String removeLinkId : removeLinkIds) {
            linkService.removeLink(removeLinkId);
        }

        List<Link> findLinks = careerBoard.getLinks();
        List<Link> updateLinks = updateLinkRequestDtos.stream()
                .map(UpdateLinkRequestDto::toLinkWithId)
                .toList();

        Map<String, Link> updateLinksMap = updateLinks.stream()
                .collect(Collectors.toMap(Link::getLink, link -> link));

        for (Link findLink : findLinks) {
            findLink.updateLink(updateLinksMap.get(findLink.getLinkId()));
        }

        List<Link> addLinks = addLinkRequestDtos.stream()
                .map(AddLinkRequestDto::toLinkWithoutId)
                .toList();

        for (Link addLink : addLinks) {
            careerBoard.addLink(addLink);
        }
    }

    @Transactional
    public void saveSkills(
            User user,
            List<String> links
    ) {
        CareerBoard careerBoard = getCareerBoardByUser(user);
        careerBoard.updateSkills(links);
    }
}
