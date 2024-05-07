package project_spring.board.repository;

import org.apache.ibatis.annotations.*;
import project_spring.board.DTO.DTO;
import project_spring.board.DTO.board;

import java.util.List;

@Mapper
public interface mapper {

    @Insert("insert into user_info values(#{user_id},#{user_pw},#{user_name},#{user_regidnet1},#{user_regidnet2})")
    public int User_insert(String user_id, String user_pw, String user_name, String user_regidnet1, String user_regidnet2);

    @Select("SELECT * FROM USER_INFO")
    public List<DTO> Select_User();

    @Select("Select * from user_info where id = #{user_id} and pw = #{user_pw}")
    public List<DTO> login_user(String user_id, String user_pw);

    @Select("SELECT * FROM USER_INFO where #{user_id}")
    public List<DTO> login_user2(String user_id);

    @Insert("INSERT INTO board(user_id, board_title, board_content) VALUES (#{user_id}, #{board_title}, #{board_content})")
    public int insert_board(String user_id, String board_title, String board_content);

    @Select("select * from board")
    public List<board> select_board();

    @Delete("DELETE from board where user_id = #{user_id}")
    public int delete_board(String user_id);

    @Insert("INSERT INTO comment (board_id, user_id, comment_content) VALUES (#{board_no}, #{user_id}, #{comment_content})")
    public int insert_comment(String user_id, String board_no, String comment_content);

    @Update("update User_Info set user_pw = #{user_pw} where user_id = #{user_id}")
    public int update_user_pw(String user_id, String user_pw);

}
