package careerlog.server.resume.service;


import careerlog.server.common.response.exception.CustomException;
import careerlog.server.common.response.resultcode.ResultCode;
import careerlog.server.resume.domain.Resume;
import careerlog.server.resume.domain.ResumeItem;
import careerlog.server.resume.domain.ResumeType;
import careerlog.server.resume.repository.ResumeRepository;
import careerlog.server.resume.repository.ResumeTypeRepository;
import careerlog.server.user.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ResumeService {

    private final ResumeRepository resumeRepository;
    private final ResumeTypeRepository resumeTypeRepository;

    // 추후 확장 가능한 기능 (1계정당 관리하는 이력서가 많아지는 경우)
    public List<Resume> getResumesByUser(User user) {
        return resumeRepository.findResumesByUser(user);
    }

    public List<ResumeType> getResumeTypes() {
        return resumeTypeRepository.findAll();
    }

    public void addResume(User user, List<ResumeItem> resumeItems, String resumeTypeCode) {
        // userId는 다운로드 받은 이력서를 파일에 저장할 때, 필요

        Resume resume = Resume.builder()
                .resumeType(getResumeType(resumeTypeCode))
                .resumeItems(resumeItems)
                .user(user)
                .build();

        resumeRepository.save(resume);

        /*
        TODO : 이력서 제작하는 로직
        1. 이력서 제작 완료 후 fileService 를 통해서 파일 저장
        2. 최종적으로 저장된 파일의 Url을 전달받고 다운로드 진행
        결과적으로는 ReturnType은 String이 되어야 함
         */
    }

    private ResumeType getResumeType(String resumeTypeCode) {
        // TODO : NO RESUME TYPE EXCEPTION / RESUME EXCEPTION 로 변경
        return resumeTypeRepository.findById(resumeTypeCode)
                .orElseThrow(() -> new CustomException(ResultCode.INTERNAL_SERVER_ERROR));
    }
}
