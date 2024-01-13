package example.org.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.ApplicationPidFileWriter;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

//@EnableJpaAuditing        //hello test를 작동시키기 위해 @SpringBootApplication과 분리해야한다.
@SpringBootApplication
public class Application {          //프로젝트의 메인 클래스, 프로젝트의 최상단에 위치해야 한다.
    public static void main(String[] args) {
//        SpringApplication.run(Application.class, args);
        SpringApplication application = new SpringApplication(Application.class);
        application.addListeners(new ApplicationPidFileWriter());
        application.run(args);
    }
}
