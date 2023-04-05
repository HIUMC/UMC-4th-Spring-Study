package study.datajpa.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of={"id", "username", "age"}) // value 찍을 때 편리하다. 다만 team 넣었다가는 연관관계 타고 넘어가서 다출력하니 금지!
public class Member extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String username;

    private int age;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Team team;

    /**
     * JPA 표준 스펙상 Entity 는 디폴트 기본생성자가 하나 존재해야함.
     * private 까지는 안되고 protected 를 추천한다.
     * private 를 해버리면 프록시 기능이 동작하지 않는다.
     * ->@NoArgsConstructor 로 처리가능
     */

    public Member(String username) {
        this.username = username;
    }

    public Member(String username, int age, Team team) {
        this.username = username;
        this.age = age;
        if (team != null) {
            changeTeam(team);
        }
    }


    public Member(String username, int age) {
        this.username = username;
        this.age = age;
    }

    public void changeTeam(Team team) {
        this.team = team;
        team.getMembers().add(this);
        /**
         * team 에 가서도 team 의 멤버에 추가를 해주어야한다.
         * member 는 소속 팀을 옮기고, team 은 새로운 멤버를 추가하는 것이다.
         */
    }

}
