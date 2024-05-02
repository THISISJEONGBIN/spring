package project_spring.board.ctrl;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import project_spring.board.DTO.DTO;
import project_spring.board.DTO.DTObuilder;
import project_spring.board.repository.mapper;

import java.util.Iterator;
import java.util.List;

@Controller
public class Control {
    @Autowired
    private mapper map;

    @GetMapping("/")
    public String index() {

        return "index.html";
    }

    @PostMapping("/")
    public String index2(@RequestParam String username, @RequestParam String password, Model mo) {
        List<DTO> li = map.Select_User();
        for (DTO dto : li) {
            if (dto.getId().equals(username) && dto.getPw().equals(password)) {
                mo.addAttribute("user", dto);
            }
        }

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
        System.out.println(user_id);
        System.out.println(user_pw);
        System.out.println(user_name);
        System.out.println(user_regident1);
        System.out.println(user_regident2);
        DTO a = new DTObuilder().id(user_id).pw(user_pw).name(user_name).resident_1(Integer.parseInt(user_regident1)).resident_2(Integer.parseInt(user_regident2)).build();
        map.User_insert(a.getId(), a.getPw(), a.getName(), Integer.toString(a.getResident_1()), Integer.toString(a.getResident_2()));

        return "user/login.html";
    }


    @GetMapping("/userlogin")
    public String UserLogin() {
        return "user/login.html";
    }

    @PostMapping("/userlogin")
    public String UserLogin2(@RequestParam String user_id, @RequestParam String user_pw) {

        List<DTO> li = map.Select_User();
        int key = 0;
        for (DTO dto : li) {
            if (dto.getId().equals(user_id) && dto.getPw().equals(user_pw)) {
                key = 1;
            }
        }
        if (key == 1) {
            return "mypage.html";
        } else {
            return "user/login.html";
        }
    }

    @PostMapping("/board")
    public String board(@RequestParam String username, @RequestParam String password,
                        Model mo, HttpServletRequest req, HttpServletResponse res) {

        List<DTO> li = map.Select_User();
        for (DTO dto : li) {
            if (dto.getId().equals(username) && dto.getPw().equals(password)) {
                mo.addAttribute("user", dto);
                Cookie coo = new Cookie("username", dto.getId());
//                coo.setMaxAge(-1);
                coo.setPath("/");
                res.addCookie(coo);
                HttpSession session = req.getSession();
                session.setAttribute("user", dto);
            }
        }

        return "board/board.html";
    }
    @GetMapping("/board/insert")
    public String insert(@RequestParam String username, @RequestParam String password,
                         Model mo) {

        List<DTO> li = map.Select_User();
        for (DTO dto : li) {
            if (dto.getId().equals(username) && dto.getPw().equals(password)) {
                mo.addAttribute("user", dto);
            }
        }

        return "board/board_insert.html";
    }

    @PostMapping("/board/insert")
    public String insert(@RequestParam String insert_title, @RequestParam String insert_board) {

        System.out.println(insert_title);
        System.out.println(insert_board);
        DTO a = new DTObuilder().board_title(insert_title).board_content(insert_board).build();
//        map.insert_board(a.getBoard_title(), a.getBoard_content());

        return "board/board_insert.html";
    }
    @GetMapping("/board/select")
    public String select() {

        return "board/board_select.html";
    }
}
