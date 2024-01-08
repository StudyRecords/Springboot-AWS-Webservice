package example.org.springboot.web.dto;

import example.org.springboot.domain.posts.Posts;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostsListResponseDto {
    private Long id;
    private String title;
//    private String content;       //게시글 목록 조회에 내용은 필요 없으니까~
    private String author;
    private LocalDateTime modifiedDate;

    public PostsListResponseDto(Posts post){
        this.id = post.getId();
        this.title = post.getTitle();
        this.author = post.getAuthor();
        this.modifiedDate = post.getModifiedDate();
    }
}
