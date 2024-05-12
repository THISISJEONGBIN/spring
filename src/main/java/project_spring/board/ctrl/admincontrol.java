package project_spring.board.ctrl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import project_spring.board.DTO.DTO;
import project_spring.board.repository.mapper;

import java.util.List;

@Controller
@RequestMapping("/admin")

public class admincontrol {

    @Autowired
    mapper map;

    @GetMapping("/admin/seluser")
    public String userdel(Model mo) {

        List<DTO> li =  map.Select_User();
        mo.addAttribute("user", li);
        return "admin/admin_select_user.html";
    }
}
