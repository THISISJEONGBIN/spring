package project_spring.board.ctrl;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import project_spring.board.DTO.DTO;
import project_spring.board.DTO.DTObuilder;
import project_spring.board.repository.mapper;

import java.net.Authenticator;
import java.util.List;

@Controller
public class Control {

    @Autowired
    private mapper map;

    @GetMapping("/")
    public String index() {

        return "index.html";
    }

    @GetMapping("/admin")
    public String admin() {

        return "admin/admin_index.html";
    }

    @GetMapping("/singup")
    public String singup() {

        return "user/singup.html";
    }

    @PostMapping("/singup")
    public String singup2(@RequestParam String user_id, @RequestParam String user_pw, @RequestParam String user_name
            , @RequestParam String user_regident1, @RequestParam String user_regident2) {
        DTO a = new DTObuilder().id(user_id).pw(user_pw).name(user_name).resident_1(Integer.parseInt(user_regident1)).resident_2(Integer.parseInt(user_regident2)).build();
        map.User_insert(a.getId(), a.getPw(), a.getName(), Integer.toString(a.getResident_1()), Integer.toString(a.getResident_2()));

        return "user/login.html";
    }


    @GetMapping("/userlogin")
    public String UserLogin() {
        return "user/login.html";
    }

    @PostMapping("/userlogin")
    public String UserLogin2() {

        return "user/login.html";
    }

    @GetMapping("/board")
    public String board() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = (String)authentication.getName();
        return "board/board.html";
    }

    @PostMapping("/board")
    public String board(@RequestParam String username, @RequestParam String password,
                        Model mo, HttpServletRequest req, HttpServletResponse res) {

        List<DTO> li = map.Select_User();
        for (DTO dto : li) {
            if (dto.getId().equals(username) && dto.getPw().equals(password)) {
                mo.addAttribute("user", dto);
                Cookie coo = new Cookie("username", String.valueOf(dto.getId()));
//                coo.setMaxAge(-1);
                coo.setPath("/");
                System.out.println( dto.getId() + "로그인 성공");
                res.addCookie(coo);
                HttpSession session = req.getSession();
                session.setAttribute("user", dto);
            }
        }

        return "board/board.html";
    }
    @GetMapping("/board/insert")
    public String insert(Model mo) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = (String)authentication.getName();
        System.out.println(username + "안녕");
        mo.addAttribute("username", username);

        return "board/board_insert.html";
    }

    @PostMapping("/board/insert")
    public String insert(@RequestParam String insert_title, @RequestParam String insert_board, Model mo) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = (String)authentication.getName();
        System.out.println(username + "님이 게시물 작성");

        System.out.println(insert_title);
        System.out.println(insert_board);
        map.insert_board(username, insert_title, insert_board);

        return "board/board_insert.html";
    }
    @GetMapping("/board/select")
    public String select() {

        return "board/board_select.html";
    }
}
