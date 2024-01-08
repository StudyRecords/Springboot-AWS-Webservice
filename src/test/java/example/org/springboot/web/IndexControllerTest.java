package example.org.springboot.web;

import example.org.springboot.domain.posts.Posts;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class IndexControllerTest {
    @LocalServerPort private int port;
    @Autowired private TestRestTemplate restTemplate;

    @Test
    public void 메인페이지_로딩(){
        //given

        //when
        String body = this.restTemplate.getForObject("/", String.class);

        //then
        assertThat(body).contains("스프링 부트로 시작하는 웹 서비스");
    }

    @Test
    public void 게시글_작성_화면_로딩(){
        //given

        //when
        String body = this.restTemplate.getForObject("/posts/save", String.class);

        //then
        assertThat(body).contains("등록");
    }

}