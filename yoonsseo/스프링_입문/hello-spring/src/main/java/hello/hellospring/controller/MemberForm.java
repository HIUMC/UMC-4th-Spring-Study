package hello.hellospring.controller;

public class MemberForm {
    private String name;
    //이 name과 createMemberForm.html의 name이 매칭되며 값이 들어올 것

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
