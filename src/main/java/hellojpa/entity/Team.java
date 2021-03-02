package hellojpa.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Team {

    @Id @GeneratedValue
    private Long id;
    private String name;

    /**
     *  @OneToMany :
     *  Team 에도 Member 리스트를 맵핑함으로서 양방향 맵핑이 된다.
     *  mappedBy 옵션은 객체와 테이블간의 연관관계 맺는 차이를 이해해야 한다.
     *  - 객체 : 사실 양방향이 아니라, 양쪽에서 단방향 연관관계를 갖는 것이다.
     *  - 테이블 : FK 하나로 어디든 갈 수 있는 양방향 연관관계를 갖고 있다.
     *
     *  테이블의 경우 MEMBER 의 TEAM_ID 가 변경되면 TEAM 에서도 변경이 반영된다. (외래키를 공유하기 때문)
     *
     *  그러나 객체에서는
     *  Member 와 Team 은 각각 관리해줘야 한다. (이것이 문제다.)
     *  member.setTeam(exTeam) 을 해도 exTeam 의 members 에는 'member' 객체가 추가되지 않았다.
     *
     *  그래서 연관관계의 주인(Owner) 라는 개념이 나타났다.
     *  두 객체중 하나만 연관관계의 주인으로 정할 수 있다. 하나의 객체가 수정되면 반대쪽에도 반영된다.
     *  주인에는 mappedBy 사용 X, 주인이 아닌쪽에 mappedBy 설. -> Member 가 주인
     *  - 주인만이 외래키를 관리(등록, 수정) 할 수 있다.
     *  - 주인이 아닌쪽은 조회만 가능하다.
     *
     *  누구를 주인으로?
     *  - 외래키가 있는 곳을 주인으로 정한다.
     *  - 개발자들이 인지를 해야하기 때문이다.
     *
     *  단뱡향으로 ORM 매핑이 다 끝난 것이다. 끝내고, 조회기능을 더하기 위해서만 양방향을 뚫도록 하자. (나중에 뚫어도 DB 테이블에 영향을 주지 않는다.)
    */
    @OneToMany(mappedBy = "team")
    List<Member> members = new ArrayList<>();

    public List<Member> getMembers() {
        return members;
    }

    public void setMembers(List<Member> members) {
        this.members = members;
    }

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
}
