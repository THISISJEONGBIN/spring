package project_spring.board.ctrl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import project_spring.board.DTO.board;
import project_spring.board.DTO.comment;
import project_spring.board.repository.mapper;

import java.util.List;

@Controller
@RequestMapping("/board")
public class boardcontrol {
    @Autowired
    mapper map;

    @GetMapping("/insert")
    public String insert(Model mo) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = (String)authentication.getName();
        System.out.println("야생의 " + username + "이 나타났다!");
        mo.addAttribute("username", username);

        return "board/board_insert.html";
    }

    @PostMapping("/insert")
    public String insert(@RequestParam String insert_title, @RequestParam String insert_board, Model mo) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = (String)authentication.getName();

        System.out.println(username + "님이 게시물 작성");

        System.out.println(insert_title);
        System.out.println(insert_board);
        map.insert_board(username, insert_title, insert_board);

        return "board/board_insert.html";
    }

    @PostMapping("/select")
    public String Post_select( @RequestParam String insert_title, @RequestParam String insert_board,Model mo) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = (String)authentication.getName();
        map.insert_board(username, insert_title, insert_board);

        List<board> li = map.select_board();
        mo.addAttribute("board", li);

        return "board/board_select.html";
    }

    @GetMapping("/select")
    public String Get_select( Model mo) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = (String)authentication.getName();

        List<board> li = map.select_board();
        mo.addAttribute("board", li);

        return "board/board_select.html";
    }

    @GetMapping("/{board_id}")
    public String comment(@PathVariable String board_id, Model mo) {
        List<board> li = map.select_board_by_id(board_id);
        mo.addAttribute("board", li);

        List<comment> comment_li = map.select_comment(board_id);
        mo.addAttribute("comment", comment_li);

        return "board/board_comment.html";
    }

    @PostMapping("/{board_id}")
    public String post_comment(@PathVariable String board_id, @RequestParam String comment_content, Model mo) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = (String)authentication.getName();

        map.insert_comment(username,board_id,comment_content);

        List<comment> li = map.select_comment(board_id);
        mo.addAttribute("comment", li);
        return "redirect:/board/{board_id}";
    }

    @GetMapping("/{board_id}/{comment_id}")
    public String delete_comment(@PathVariable String board_id,@PathVariable String comment_id, Model mo) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = (String)authentication.getName();

        map.delete_comment(comment_id,username,board_id);
        return "redirect:/board/{board_id}";
    }

}
