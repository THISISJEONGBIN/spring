package project_spring.board.DTO;

public class DTObuilder {
    private String id, pw, name;
    private int resident_1, resident_2;

    public DTObuilder id(String id) {
        this.id = id;
        return this;
    }
    public DTObuilder pw(String pw) {
        this.pw = pw;
        return this;
    }
    public DTObuilder name(String name) {
        this.name = name;
        return this;
    }
    public DTObuilder resident_1(int resident_1) {
        this.resident_1 = resident_1;
        return this;
    }
    public DTObuilder resident_2(int resident_2) {
        this.resident_2 = resident_2;
        return this;
    }
    public DTO build() {
        return new DTO(id, pw, name, resident_1, resident_2);
    }
}
