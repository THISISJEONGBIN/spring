package project_spring.board.ctrl;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import project_spring.board.DTO.DTO;
import project_spring.board.DTO.DTObuilder;
import project_spring.board.DTO.board;
import project_spring.board.DTO.comment;
import project_spring.board.repository.mapper;

import java.util.Iterator;
import java.util.List;

@Controller
public class Control {

    @Autowired
    private mapper map;

    @GetMapping("/")
    public String index() {

        return "redirect:/userlogin";
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
    public String board(Model mo) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = (String)authentication.getName();

        System.out.println("야생의 " + username + "이 나타났다!");
        mo.addAttribute("username", username);


        return "board/board.html";
    }

    @PostMapping("/board")
    public String board(@RequestParam(name = "username") String id, @RequestParam(name = "password") String pw,
                        Model mo, HttpServletRequest req, HttpServletResponse res) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = (String)authentication.getName();
        mo.addAttribute("username", username);

        List<DTO> li = map.Select_User();
        for (DTO dto : li) {
            if (dto.getId().equals(id) && dto.getPw().equals(pw)) {
                mo.addAttribute("user", dto);
                Cookie coo = new Cookie("username", String.valueOf(dto.getId()));
//                coo.setMaxAge(-1);
                coo.setPath("/");
                System.out.println("야생의 " +dto.getId() + "이 나타났다 !");
                res.addCookie(coo);
                HttpSession session = req.getSession();
                session.setAttribute("user", dto);
            }
        }

        return "board/board.html";
    }
    @GetMapping("/mypage")
    public String mypage(Model mo) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = (String)authentication.getName();
        List<DTO> user_li = map.Select_User();
        List<board> board_li = map.mypage_board(username);
        for (DTO dto : user_li) {
            if (dto.getId().equals(username)) {
                mo.addAttribute("user", dto);
                mo.addAttribute("board", board_li);
                return "board/mypage.html";
            }
        }
     return "board/mypage.html";
    }

    @GetMapping("/mypage/changepassword")
    public String changepassword(Model mo) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = (String)authentication.getName();

        mo.addAttribute("username", username);

        return "board/chpassword.html";
    }
    @PostMapping("/mypage")
    public String changepassword(@RequestParam String oldpassword, @RequestParam String newpassword1, Model mo) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = (String)authentication.getName();

        map.update_user_pw(username, newpassword1, oldpassword);

        return "redirect:/userlogin";
    }

    @GetMapping("/mypage/{board_id}")
    public String delete_board(@PathVariable String board_id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = (String)authentication.getName();

        map.delete_board(board_id, username);

        return "redirect:/mypage";
    }

    @GetMapping("/update/{board_id}")
    public String update_board(@PathVariable String board_id) {

        return "board/board_update.html";
    }

    @PostMapping("/update/{board_id}")
    public String update_board2(@PathVariable String board_id, @RequestParam String update_title, @RequestParam String update_board) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = (String)authentication.getName();

        map.update_board(board_id,username,update_title,update_board);

        return "redirect:/mypage";
    }
}

