package hellojpa.entity;

import javax.persistence.*;
import java.util.Date;

/**
 *  매핑 어노테이션들 (@Column, @Temporal, @Enumerated, @Lob, @Transient)
 *  DB 에 어떻게 맵핑될지 맵핑 정보다. 자바 코드에 영향을 주는 것이 아님.
 */

@Entity
public class Member {
    /**
     *  식별자 매핑 (@Id, @GeneratedVAlue)
     *  @Id : 해당 필드를 PK 로 직접 지정한다.
     *  @GeneratedValue : 전략을 지정할 수 있다.
     *  - AUTO : dialect 에 맞게 자동으로 지정. 아래 3가지 옵션 중 자동 선택된다. (기본값)
     *  - IDENTITY : DB 에 설정을 위임. MYSQL 의 경우 AUTO_INCREMENT
     *  - SEQUENCE : DB 의 시퀀스 오브젝트를 사용 (오라클의 SEQUENCE)
     *  - TABLE : 키 생성용 테이블을 따로 만들어서 관리
     *  권장 : Long + 대체키(시퀀스or비즈니스랑 무관한 값) + 키 생성전략 사용. (주민번호도 변할 수 있어 부적절함)
     */
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * @Column : DB 컬럼과 맵핑시킨다.
     * 옵션은 name, length, insertable, updatable, nullable(DDL 생성시), unique(DDL 생성시) 등이 있다.
     * 아래 name 옵션 : DB의 "USERNAME" 과 이 객체의 name 을 맵핑한다.
     */
    @Column(name = "USERNAME", length = 20)
    private String name;

    private int age;

    /**
     * @Temporal : 시간과 관련된 컬럼으로 맵핑시켜주는 어노테이션
     */
    @Temporal(TemporalType.TIMESTAMP)
    private Date regDate;

    /**
     * @Enumerated : enum 을 주어진 설정으로 DB에 맵핑시켜주는 어노테이션.
     * 현업에선 String 을 써야한다. ordinal(기본값) 을 쓰면 순서(숫자)가 들어가기 때문.
     */
    @Enumerated(EnumType.STRING)
    private MemberType memberType;

    /**
     * @Lob
     * 컨텐츠 길이가 길때 바이너리파일로 넣어야하는데 이때 @Lob 을 쓴다.
     * CLOB 은 캐릭터Lob
     * BLOB 은 바이트Lob
     * 필드 타입에 따라 알아서 DB에 맵핑된다.
     */
    @Lob
    private String lobString;

    @Lob
    private byte[] lobByte;

    /**
     *  @Transient : 객체에는 존재하지만 DB 에는 매핑되지 않는 필드가 된다.
     *  가능하면 안쓰는게 좋다.
     */
    @Transient
    private boolean secretFlag;

    public void setAge(int age) {
        this.age = age;
    }

    public int getAge() {
        return age;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
}
