package project_spring.board.DTO;

import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class board {
    int board_no;
    String user_id;
    String board_title;
    String board_content;
    String created_at;
}
