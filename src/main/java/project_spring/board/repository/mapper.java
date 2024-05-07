package project_spring.board.repository;

import org.apache.ibatis.annotations.*;
import project_spring.board.DTO.DTO;
import project_spring.board.DTO.board;
import project_spring.board.DTO.comment;

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

    @Select("select * from board where board_id = #{board_id}")
    public List<board> select_board_by_id(String board_id);

    @Delete("delete from board where board_id = #{board_id} and user_id = #{user_id}")
    public int delete_board(String board_id, String user_id);

    @Insert("INSERT INTO comment (board_id, user_id, comment_content) VALUES (#{board_id}, #{user_id}, #{comment_content})")
    public int insert_comment(String user_id, String board_id, String comment_content);

    @Update("update User_Info set user_pw = #{new_user_pw} where user_id = #{user_id} and user_pw = #{old_user_pw};")
    public int update_user_pw(String user_id, String new_user_pw, String old_user_pw);

    @Update("update board set board_title = #{board_title}, board_content = #{board_content} where board_id = #{board_id} and user_id = #{user_id}")
    public int update_board(String board_id,String user_id, String board_title, String board_content);

    @Select("SELECT * from board where user_id = #{user_id}")
    public List<board> mypage_board(String user_id);

    @Select("select * from comment where board_id = #{board_id}")
    public List<comment> select_comment(String board_id);

    @Delete("delete from comment where comment_id = #{comment_id} and user_id = #{user_id} ")
    public int delete_comment(String comment_id, String user_id, String board_id);
}
