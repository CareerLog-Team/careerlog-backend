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
     */

    @Transactional
    public void saveActivity(
            CareerBoard careerBoard,
            List<String> removeActivityIds,
            List<Activity> updateActivities,
            List<Activity> addActivities
    ) {
        // 1. 항목 제거
        for (String removeActivityId : removeActivityIds) {
            activityService.removeActivityById(removeActivityId);
        }

        // 2. 업데이트
        List<Activity> findActivities = careerBoard.getActivities();
        Map<String, Activity> updateActivitiesMap = updateActivities.stream()
                .collect(Collectors.toMap(Activity::getActivityId, activity -> activity));

        for (Activity findActivity : findActivities) {
            findActivity.updateActivity(updateActivitiesMap.get(findActivity.getActivityId()));
        }

        // 3. 항목 추가
        for (Activity addActivity : addActivities) {
            careerBoard.addActivity(addActivity);
        }
    }

    @Transactional
    public void saveActivity2(
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

    @Transactional
    public void saveCertificate(
            CareerBoard careerBoard,
            List<String> removeCertificateIds,
            List<Certificate> updateCertificates,
            List<Certificate> addCertificates
    ) {
        // 1. 삭제 로직
        for (String removeCertificateId : removeCertificateIds) {
            certificateService.removeCertificate(removeCertificateId);
        }

        // 2. 수정 로직
        List<Certificate> findCertificates = careerBoard.getCertificates();
        Map<String, Certificate> updateCertificatesMap = updateCertificates.stream()
                .collect(Collectors.toMap(Certificate::getCertificateId, certificate -> certificate));

        for (Certificate findCertificate : findCertificates) {
            findCertificate.updateCertificate(updateCertificatesMap.get(findCertificate.getCertificateId()));
        }

        for (Certificate addCertificate : addCertificates) {
            careerBoard.addCertificate(addCertificate);
        }
    }

    @Transactional
    public void saveEducation(
            CareerBoard careerBoard,
            List<String> removeEducationIds,
            List<Education> updateEducations,
            List<Education> addEducations
    ) {
        // 1. 삭제 로직
        for (String removeEducationId : removeEducationIds) {
            educationService.removeEducation(removeEducationId);
        }

        List<Education> findEducations = careerBoard.getEducations();
        Map<String, Education> updateEducationsMap = updateEducations.stream()
                .collect(Collectors.toMap(Education::getEducationId, education -> education));

        for (Education findEducation : findEducations) {
            findEducation.updateEducation(updateEducationsMap.get(findEducation.getEducationId()));
        }

        for (Education addEducation : addEducations) {
            careerBoard.addEducation(addEducation);
        }
    }

    @Transactional // Transactional을 달고있는 Method는 private를 할 수 없음
    public void saveLanguage(
            CareerBoard careerBoard,
            List<String> removeLanguageIds,
            List<Language> updateLanguages,
            List<Language> addLanguages
    ) {
        for (String removeLanguageId : removeLanguageIds) {
            languageService.removeLanguage(removeLanguageId);
        }

        List<Language> findLanguages = careerBoard.getLanguages();
        Map<String, Language> updateLanguagesMap = updateLanguages.stream()
                .collect(Collectors.toMap(Language::getLanguageId, language -> language));

        for (Language findLanguage : findLanguages) {
            findLanguage.updateLanguage(updateLanguagesMap.get(findLanguage.getLanguageId()));
        }

        for (Language addLanguage : addLanguages) {
            careerBoard.addLanguage(addLanguage);
        }
    }

    @Transactional
    public void saveLink (
            CareerBoard careerBoard,
            List<String> removeLinkIds,
            List<Link> updateLinks,
            List<Link> addLinks
    ) {
        for (String removeLinkId : removeLinkIds) {
            linkService.removeLink(removeLinkId);
        }

        List<Link> findLinks = careerBoard.getLinks();
        Map<String, Link> updateLinksMap = updateLinks.stream()
                .collect(Collectors.toMap(Link::getLink, link -> link));

        for (Link findLink : findLinks) {
            findLink.updateLink(updateLinksMap.get(findLink.getLinkId()));
        }

        for (Link addLink : addLinks) {
            careerBoard.addLink(addLink);
        }
    }

    @Transactional
    public void saveSkills(
            CareerBoard careerBoard,
            List<String> links
    ) {
        careerBoard.updateSkills(links);
    }
}
