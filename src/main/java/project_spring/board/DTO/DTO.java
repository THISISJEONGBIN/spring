package project_spring.board.DTO;


public class DTO {

    private String user_id, user_pw, user_name, board_title, board_content;
    private int user_resident_1, user_resident_2;

    public DTO(String id, String pw, String name, int resident_1, int resident_2, String board_title, String board_content) {
        this.user_id = id;
        this.user_pw = pw;
        this.user_name = name;
        this.user_resident_1 = resident_1;
        this.user_resident_2 = resident_2;
        this.board_title = board_title;
        this.board_content = board_content;
    }
    public String getId() {
        return user_id;
    }
    public String getPw() {
        return user_pw;
    }
    public String getName() {
        return user_name;
    }
    public int getResident_1() {
        return user_resident_1;
    }
    public int getResident_2() {
        return user_resident_2;
    }
    public String getBoard_title() {
        return board_title;
    }
    public String getBoard_content() {
        return board_content;
    }


}
