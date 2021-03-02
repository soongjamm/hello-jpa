package hellojpa;

import hellojpa.entity.Member;
import hellojpa.entity.Team;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class Main {
    public static void main(String[] args) {
        /**
         *  EntityManagerFactory 는 EntityManager 를 만드는 팩토리다.
         *  - 하나만 생성해서 앱 전체에서 공유해야한다.
         *  - DB 접속, 생성을 하니 계속하면 성능에 문제가 생긴다.
         *
         *  Persistence 를 통해서 'hello' 라는 이름의 설정정보를 읽어들이고 DB와 연결한다.
         *
         */
        EntityManagerFactory emf =
                Persistence.createEntityManagerFactory("hello");

        /**
         *  EntityManager
         *  실제 웹 애플리케이션에서는 고객의 요청이 들어올 때 마다 생성해서 사용해야 한다.
         *  - 쓰레드간에 공유하면 안되고 사용자 요청이 오면 쓰고 바로 버려야한다.
         *  - 새로운 요청이 오면 새로 만들어야한다.
         *  - 공유하면 다른 트랜잭션과 섞이면서 일관성에 문제가 생긴다.(내생각)
         *
         *  EntityManger 를 통해서 트랜잭션을 얻어야 한다.
         *  - EntityManager 는 트랜잭션이 진행될 때 마다 필요하고
         *  - EntityManager 가 곧 우리가 생각하는 JPA 라고 생각하면 된다.
         *
         *  JPA 의 모든활동은 트랜잭션을 얻어서 그 안에서 이루어져야 한다.
         *  - 모든 데이터 변경은 트랜잭션 안에서 실행되어야 한다.
         *  - (스프링에서는 @Transactional 안에서)
         */
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            // 비즈니스 로직 작성
            /**
             * 객체지향 모델링 (ORM 맵핑)
             * 단방향 관계
             */
            Team team = new Team();
            team.setName("fc seoul");
            em.persist(team);

            Member member = new Member();
            member.setName("kim");
            member.setAge(13);
            member.setTeam(team); // teamId 가 아닌, 객체 자체를 바로 저장
            em.persist(member);

            //
            em.flush();
            em.clear();

            /**
             * Team해 을 조회한다.
             * 외래키를 사용하지 않고, findMember 객체의 참조를 통해 바로 Team 을 찾을 수 있다.
             */
            Member findMember = em.find(Member.class, member.getId());
            Team findTeam = em.find(Team.class, findMember.getTeam());

            findTeam.getName();

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            /**
             *  EntityManager 는 다 쓰면 꼭 닫아줘야 한다.
             *  데이터베이스 리소스를 사용하고 있기 때문에 닫지 않으면 문제가 생길 수 있다.
             */
            em.close();
        }


        System.out.println("hello");

        emf.close(); // 프로젝트가 전체적으로 끝나면 웹 애플리케이션을 내린다고 생각하면 된다.
    }
}
