package example.org.springboot.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import example.org.springboot.domain.posts.Posts;
import example.org.springboot.domain.posts.PostsRepository;
import example.org.springboot.web.dto.PostsSaveRequestDto;
import example.org.springboot.web.dto.PostsUpdateRequestDto;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;


import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@AutoConfigureMockMvc
public class PostsApiControllerTest {

    @LocalServerPort private int port;
    @Autowired TestRestTemplate restTemplate;
    @Autowired PostsRepository postsRepository;
    @Autowired private WebApplicationContext context;
    private MockMvc mvc;

    @Before
    public void setup(){
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @After
    public void tearDown() throws Exception{
        postsRepository.deleteAll();
    }

    @Test
    @WithMockUser(roles="USER")
    public void Posts_등록() throws Exception {
        //given
        String title = "테스트 제목";
        String content = "테스트 내용";
        PostsSaveRequestDto requestDto = PostsSaveRequestDto.builder()
                .title(title)
                .content(content)
                .author("이영선")
                .build();

        String url = "http://localhost:" + port + "/api/v1/posts";

        //when
//        ResponseEntity<Long> responseEntity = restTemplate.postForEntity(url, requestDto, Long.class);
        mvc.perform(post(url).contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(new ObjectMapper().writeValueAsString(requestDto)))
                .andExpect(status().isOk());


        //then
//        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
//        assertThat(responseEntity.getBody()).isGreaterThan(0L);

        List<Posts> postsList = postsRepository.findAll();
        assertThat(postsList.get(0).getTitle()).isEqualTo(title);
        assertThat(postsList.get(0).getContent()).isEqualTo(content);
    }

    @Test
    @WithMockUser(roles="USER")
    public void Posts_수정() throws Exception {
        //given
        Posts savedPosts = postsRepository.save(Posts.builder()
                .title("기존 제목")
                .content("기존 내용")
                .author("이영선")
                .build());
        Long updateId = savedPosts.getId();
        String expectesTitle = "수정된 테스트 제목";
        String expectedContent = "수정된 테스트 내용";

        PostsUpdateRequestDto requestDto = PostsUpdateRequestDto.builder()
                .title(expectesTitle)
                .content(expectedContent)
                .build();

        String url = "http://localhost:" + port + "/api/v1/posts/"+updateId;
//        HttpEntity<PostsUpdateRequestDto> requestEntity = new HttpEntity<>(requestDto);

        //when
//        ResponseEntity<Long> responseEntity = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, Long.class);
        mvc.perform(put(url).contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(new ObjectMapper().writeValueAsString(requestDto)))
                .andExpect(status().isOk());

        List<Posts> postsList = postsRepository.findAll();

        //then
//        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
//        assertThat(responseEntity.getBody()).isGreaterThan(0L);
        assertThat(postsList.get(0).getTitle()).isEqualTo(expectesTitle);
        assertThat(postsList.get(0).getContent()).isEqualTo(expectedContent);
    }
}
