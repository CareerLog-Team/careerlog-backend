package careerlog.server.workrecord.service;


import careerlog.server.careerboard.domain.Career;
import careerlog.server.common.exception.CustomException;
import careerlog.server.common.resultcode.ResultCode;
import careerlog.server.workrecord.domain.WorkRecord;
import careerlog.server.workrecord.repository.WorkRecordRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class WorkRecordService {

    private final WorkRecordRepository workRecordRepository;
    
    public List<WorkRecord> getWorkRecordsByCareers(List<Career> careers) {
        return workRecordRepository.findWorkRecordsByCareer(careers);
    }

    public WorkRecord getWorkRecord(String workRecordId) {
        return workRecordRepository
                .findById(workRecordId)
                .orElseThrow(() -> new CustomException(ResultCode.WORK_RECORD_NOT_FOUND_EXCEPTION));
    }

    @Transactional
    public void addWorkRecord(Career career, WorkRecord addWorkRecord) {
        career.addWorkRecord(addWorkRecord);
    }

    @Transactional
    public void updateWorkRecord(WorkRecord beforeWorkRecord, WorkRecord updateWorkRecord) {
        beforeWorkRecord.updateWorkRecord(updateWorkRecord);
    }

    @Transactional
    public void removeWorkRecord(WorkRecord removeWorkRecord) {
        workRecordRepository.delete(removeWorkRecord);
    }
}
