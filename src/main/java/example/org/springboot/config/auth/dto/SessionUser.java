package example.org.springboot.config.auth.dto;

import example.org.springboot.domain.user.User;
import lombok.Getter;

import java.io.Serializable;

// 인증된 사용자 정보만 필요하다. (그 외의 정보는 필요 X)
// 직렬화 기능을 가진 Session dto
@Getter
public class SessionUser implements Serializable {

    private String name;
    private String email;
    private String picture;

    public SessionUser(User user) {
        this.name = user.getName();
        this.email = user.getEmail();
        this.picture = user.getPicture();
    }
}
