package hellojpa;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Member {
    public void setTeam(Team team) {
        this.team = team;
    }

    public Locker getLocker() {
        return locker;
    }

    public void setLocker(Locker locker) {
        this.locker = locker;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }



    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    @JoinColumn(name="TEAM_ID")
    private Team team;
    @Column(name="USERNAME")
    private String username;

    @OneToMany(mappedBy = "member")
    private List<MemberProduct> memberProductList=new ArrayList<>();

    @OneToOne
    @JoinColumn(name="LOCKER_ID")
    private Locker locker;

    @Embedded
    private Address address;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name="city",
            column = @Column(name = "WORK_CITY")),
            @AttributeOverride(name="street",
            column = @Column(name = "WORK_STREET")),
            @AttributeOverride(name="zipcode",
            column = @Column(name="WORK_ZIPCODE"))
    })
    private Address homeAddress;
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Team getTeam() {
        return team;
    }

   public void changeTeam(Team team){
        this.team=team;
        team.getMembers().add(this);
   }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
