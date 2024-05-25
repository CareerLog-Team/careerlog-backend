package careerlog.server;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest(classes = CareerlogServerApplicationTests.class)
@TestPropertySource(
        properties = {
                "jwt.secret=helloworld"
        }
)
class CareerlogServerApplicationTests {

    @Test
    void contextLoads() {
    }
}
