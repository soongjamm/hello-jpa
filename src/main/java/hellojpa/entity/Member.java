package hellojpa.entity;

import javax.persistence.*;


@Entity
public class Member {

    @Id @GeneratedValue
    private Long id;

    @Column(name = "USERNAME")
    private String name;

    private int age;

    /**
     *  단방향 관계를 만들 것이다.
     *  @ManyToOne 인 이유는 Member 의 입장에서 Member 가 다수이고 Team 이 하나이기 때문이다.
     *  이제 JPA 가 알아서 TEAM_ID 라는 FK 를 만들고 연관관계 매핑해준다.
     *
     *  fetch 옵션에 LAZY 를 줌으로서 Team 을 바로 조회하지 않고, 실제로 사용될 때 조회되도록 한다.
     *  (전부 LAZY 권장. 꼭 필요한 시점에만 EAGER. 속단해서 최적화하지 말자.)
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TEAM_ID")
    private Team team;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }
}
