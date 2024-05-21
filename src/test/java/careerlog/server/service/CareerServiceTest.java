package careerlog.server.service;

import careerlog.server.careerboard.service.CareerService;
import careerlog.server.careerboard.domain.Career;
import careerlog.server.careerboard.repository.CareerRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@SpringBootTest
@Transactional
@Rollback
class CareerServiceTest {

    @Autowired private CareerService service;
    @Autowired private CareerRepository repository;

    @Test
    void 커리어_저장_테스트() {
        //given
        long beforeAddCount = repository.count();

        Career career1 = getCareer("organization1");
        Career career2 = getCareer("organization2");
        Career career3 = getCareer("organization3");
        Career career4 = getCareer("organization4");
        Career career5 = getCareer("organization5");

        ArrayList<Career> careers = new ArrayList<>();
        careers.add(career1);
        careers.add(career2);
        careers.add(career3);
        careers.add(career4);
        careers.add(career5);

        //when
        service.addCareers(careers);
        long afterAddCount = repository.count();
        long answer = afterAddCount - beforeAddCount;

        //then
        Assertions.assertThat(answer).isEqualTo(careers.size());
    }

    @Test
    void 커리어_수정_테스트() {
        //given
        Career career1 = getCareer("organization1");
        Career career2 = getCareer("organization2");

        ArrayList<Career> careers = new ArrayList<>();
        careers.add(career1);
        careers.add(career2);

        service.addCareers(careers);

        Career updateCareer1 = getCareer(career1.getCareerId(), "changeOrganization1");
        Career updateCareer2 = getCareer(career2.getCareerId(), "changeOrganization2");

        ArrayList<Career> updateCareers = new ArrayList<>();
        updateCareers.add(updateCareer1);
        updateCareers.add(updateCareer2);

        //when
        service.updateCareers(updateCareers);

        //then
        List<Career> updatedCareers = repository.findAll();

        for (int i = 0; i < updatedCareers.size(); i++) {
            Career updateCareer = updateCareers.get(i);
            Career updatedCareer = updatedCareers.get(i);

            Assertions.assertThat(updatedCareer.getCareerId()).isEqualTo(updateCareer.getCareerId());
            Assertions.assertThat(updatedCareer.getOrganization()).isEqualTo(updateCareer.getOrganization());
        }
    }

    private static Career getCareer(String organization) {
        return Career.builder()
                .organization(organization)
                .build();
    }
    private static Career getCareer(String id, String organization) {
        return Career.builder()
                .careerId(id)
                .organization(organization)
                .build();
    }

}