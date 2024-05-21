package careerlog.server.workrecord.repository;

import careerlog.server.careerboard.domain.Career;
import careerlog.server.workrecord.domain.WorkRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface WorkRecordRepository extends JpaRepository<WorkRecord, String> {

    @Query("SELECT wr FROM WorkRecord wr WHERE wr.career IN :careers")
    List<WorkRecord> findWorkRecordsByCareer(List<Career> careers);

}
