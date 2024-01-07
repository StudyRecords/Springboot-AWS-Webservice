package example.org.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class Application {          //프로젝트의 메인 클래스, 프로젝트의 최상단에 위치해야 한다.
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
