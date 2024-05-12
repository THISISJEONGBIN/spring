package project_spring.board.DTO;


public class DTO {

    private String user_id, user_pw, user_name, user_code;
    private int user_resident_1;

    public DTO(String id, String pw, String name, int resident_1, String code) {
        this.user_id = id;
        this.user_pw = pw;
        this.user_name = name;
        this.user_resident_1 = resident_1;
        this.user_code = code;
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
    public String getCode() {return user_code;}


}
