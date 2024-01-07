package example.org.springboot.web.dto;

import example.org.springboot.domain.posts.Posts;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostsSaveRequestDto {
    private String title;
    private String content;
    private String author;

    @Builder
    public PostsSaveRequestDto(String title, String content, String author){
        this.title = title;
        this.content = content;
        this.author = author;
    }

    public Posts toEntity(){
        // 생성자를 사용하지 않는 이유 : Posts 클래스에서 Builder를 사용했으므로 같은 방식으로 생성해야함
//        Posts post = new Posts(title, content, author);
//        return post;
        return Posts.builder()
                .title(title)
                .content(content)
                .author(author)
                .build();
    }
}
