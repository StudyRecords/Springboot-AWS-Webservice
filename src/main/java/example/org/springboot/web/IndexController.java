package example.org.springboot.web;

import example.org.springboot.config.auth.LoginUser;
import example.org.springboot.config.auth.dto.SessionUser;
import example.org.springboot.service.PostsService;
import example.org.springboot.web.dto.PostsResponseDto;
import example.org.springboot.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Controller
public class IndexController {      // 페이지에 관련된 컨트롤러

    private final PostsService postsService;
    private final HttpSession httpSession;

    @GetMapping("/")
    public String index(Model model, @LoginUser SessionUser user){
        model.addAttribute("posts", postsService.findAllDesc());
        // index.mustache에서 구글 소셜 로그인 할 때 userName 을 사용하므로 model에 userName을 저장해준다
//        SessionUser user = (SessionUser) httpSession.getAttribute("user");        //@LoginUser 애노테이션으로 대체됨
        if (user!=null){
            model.addAttribute("userName", user.getName());
        }
        return "index";
    }

    @GetMapping("/posts/save")
    public String postsSave(){
        return "posts-save";
    }

    @GetMapping("/posts/update/{id}")
    public String postsUpdate(@PathVariable Long id, Model model){
        PostsResponseDto responseDto = postsService.findById(id);
        model.addAttribute("post", responseDto);
        return "posts-update";
    }
}
