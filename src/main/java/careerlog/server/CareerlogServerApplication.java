package careerlog.server;

import careerlog.server.config.EnvConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
@PropertySource(value = {"classpath:env/env.yml"}, factory = EnvConfig.class)
public class CareerlogServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(CareerlogServerApplication.class, args);
    }
}
