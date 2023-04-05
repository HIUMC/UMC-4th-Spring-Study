package hello.hellospring.repository;
import hello.hellospring.domain.Member;
import org.springframework.jdbc.datasource.DataSourceUtils;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
public class JdbcMemberRepository implements MemberRepository {
    private final DataSource dataSource;
    public JdbcMemberRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    @Override
    public Member save(Member member) {
        String sql = "insert into member(name) values(?)";

        //사용한 자원들 다시 release 해주는 작업 필요
        //DB커넥션은 외부 네트워크와 연결되기 때문에 작업 끝나면 연결 끊어주는 거 중요하다
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        //결과값을 저장할 수 있다

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            //DB에 insert를 해야 id 얻을 수 있음
            pstmt.setString(1, member.getName());
            //parameterIndex 1이 sql문에서 ?와 매칭되고 member.getName()의 값 넣어주기
            pstmt.executeUpdate();
            //DB에 실제 쿼리 날려줌
            rs = pstmt.getGeneratedKeys();
            //prepareStatement의 Return-generated-keys 옵션으로 키를 받을 수 있다
            if (rs.next()) { //값이 있으면 getLong으로 꺼내오고 Id 설정
                member.setId(rs.getLong(1));
            } else {
                throw new SQLException("id 조회 실패");
            }
            return member;
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }
    }
    @Override
    public Optional<Member> findById(Long id) {
        String sql = "select * from member where id = ?";

        //사용한 자원들 다시 release 해주는 작업 필요
        //DB커넥션은 외부 네트워크와 연결되기 때문에 작업 끝나면 연결 끊어주는 거 중요하다
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, id);
            rs = pstmt.executeQuery();
            //조회니까 executeUpdate아니고 executeQuery
            if(rs.next()) { //값이 있으면 멤버 객체 쭉 만들고 반환
                Member member = new Member();
                member.setId(rs.getLong("id"));
                member.setName(rs.getString("name"));
                return Optional.of(member);
            } else {
                return Optional.empty();
            }
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }
    }
    @Override
    public List<Member> findAll() {
        String sql = "select * from member";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            List<Member> members = new ArrayList<>();
            //리스트 만들어서 루프에서 add
            while(rs.next()) {
                Member member = new Member();
                member.setId(rs.getLong("id"));
                member.setName(rs.getString("name"));
                members.add(member);
            }
            return members;
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }
    }
    @Override
    public Optional<Member> findByName(String name) {
        String sql = "select * from member where name = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, name);
            rs = pstmt.executeQuery();
            if(rs.next()) {
                Member member = new Member();
                member.setId(rs.getLong("id"));
                member.setName(rs.getString("name"));
                return Optional.of(member);
            }
            return Optional.empty();
            //없으면 empty
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }
    }
    private Connection getConnection() {
        return DataSourceUtils.getConnection(dataSource);
        //dataSource.getConnection()으로 직접 연결할 수도 있지만
        //이러면 계속 새로운 연결이 주어지고
        //스프링 프레임워크를 통해서 커넥션을 쓸 때는
        //DataSourceUtils를 통해 획득해야
        //똑같은 DB 커넥션을 유지하게 해 줌
        //커넥션 닫을 때도 DataSourceUtils 통해 닫아야 한다
    }
    private void close(Connection conn, PreparedStatement pstmt, ResultSet rs) {
        //역순으로 자원들 정리해주기
        //복잡하다
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (pstmt != null) {
                pstmt.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (conn != null) {
                close(conn);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void close(Connection conn) throws SQLException {
        DataSourceUtils.releaseConnection(conn, dataSource);
        //DataSourceUtils 통한 닫기
    }
}