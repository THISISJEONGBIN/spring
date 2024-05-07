package project_spring.board.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class comment {

    int comment_id;
    int board_id;
    String user_id;
    String comment_content;
    String created_at;
}
