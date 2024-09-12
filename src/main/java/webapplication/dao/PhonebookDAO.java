package webapplication.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import webapplication.vo.PhonebookVO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class PhonebookDAO {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    // Insert a new Phonebook entry
    public int insert(PhonebookVO phonebook) {
        // SQL 쿼리에서 시퀀스를 사용하여 id 자동 생성
        String sql = "INSERT INTO phonebook (id, name, hp, memo) VALUES (phonebook_id_seq.NEXTVAL, :name, :hp, :memo)";
        
        Map<String, Object> params = new HashMap<>();
        params.put("name", phonebook.getName());
        params.put("hp", phonebook.getHp());
        params.put("memo", phonebook.getMemo());

        KeyHolder keyHolder = new GeneratedKeyHolder();

        try {
            // KeyHolder를 사용하여 자동 생성된 ID 값 받기
            jdbcTemplate.update(sql, new MapSqlParameterSource(params), keyHolder, new String[] {"id"});
            
            // 생성된 id 값을 phonebook 객체에 설정
            phonebook.setId(keyHolder.getKey().intValue());
            
            return 1; // 성공 시 1 반환
        } catch (Exception e) {
            e.printStackTrace();
            return 0; // 실패 시 0 반환
        }
    }

    // Select all Phonebook entries
    public List<PhonebookVO> selectAll() {
        String sql = "SELECT * FROM phonebook";
        //PhonebookRowMapper를 사용해서 List<PhonebookVO>로 반환함
        return jdbcTemplate.query(sql, new PhonebookRowMapper());
    }

    // Search by phone number (hp)
    public List<PhonebookVO> search(String _search) {
        String sql = "SELECT id, name, hp, memo FROM phonebook WHERE hp LIKE :hp";
        Map<String, Object> params = new HashMap<>();
        params.put("hp", "%" + _search + "%"); // _search를 %로 감싸서 부분 검색 가능하도록 설정
        // 전화번호 리스트
        List<PhonebookVO> result = jdbcTemplate.query(sql, params, new PhonebookRowMapper());
        return result.isEmpty() ? Collections.emptyList() : result; // 찾은 게 없다면 빈 리스트 반환
    }

    // Select by ID
    public PhonebookVO selectById(int id) {
        String sql = "SELECT id, name, hp, memo FROM phonebook WHERE id = :id";
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        //id는 중복될 일이 없지만 ResultSet을 관용적으로 while(rs.next){...}로 받는 이유가 혹시 모를 예외처리인 점을 참고했습니다. 
        //+ 굳이 달리 하면 가독성 나쁠 거 같아 이렇게 했습니다.
        List<PhonebookVO> result = jdbcTemplate.query(sql, params, new PhonebookRowMapper());
        return result.isEmpty() ? null : result.get(0); //찾은 게 없다면 null 반환
    }

    // Update an existing Phonebook entry
    public int update(PhonebookVO phonebook) {
        // 먼저 해당 id가 있는지 확인하고, 없으면 실패 처리
        PhonebookVO existing = selectById(phonebook.getId());
        if (existing == null) {
            return 0; // 해당 id가 없는 경우 실패 반환
        }
        //쿼리문 작성
        String sql = "UPDATE phonebook SET name = :name, hp = :hp, memo = :memo WHERE id = :id";
        Map<String, Object> params = new HashMap<>();
        params.put("id", phonebook.getId());
        params.put("name", phonebook.getName());
        params.put("hp", phonebook.getHp());
        params.put("memo", phonebook.getMemo());

        try {
            return jdbcTemplate.update(sql, params);
        } catch (Exception e) {
            e.printStackTrace();
            return 0; // 실패 시 0 반환
        }
    }

    // Delete a Phonebook entry by ID
    public int delete(int id) {
        // 먼저 해당 id가 있는지 확인하고, 없으면 실패 처리
        PhonebookVO existing = selectById(id);
        if (existing == null) {
            return 0; // 해당 id가 없는 경우 실패 반환
        }

        String sql = "DELETE FROM phonebook WHERE id = :id";
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);

        try {
            return jdbcTemplate.update(sql, params);
        } catch (Exception e) {
            e.printStackTrace();
            return 0; // 실패 시 0 반환
        }
    }

    // RowMapper to map ResultSet to PhonebookVO object
    //jdbc를 사용해서 쿼리를 실행하면 결과가 ResultSet객체로 반환되는데, 이 결과를 java객체로 변환하는 작업이 필요
    private static class PhonebookRowMapper implements RowMapper<PhonebookVO> {
        @Override
        public PhonebookVO mapRow(ResultSet rs, int rowNum) throws SQLException {
            PhonebookVO phonebook = new PhonebookVO();
            phonebook.setId(rs.getInt("id"));
            phonebook.setName(rs.getString("name"));
            phonebook.setHp(rs.getString("hp"));
            phonebook.setMemo(rs.getString("memo"));
            return phonebook;
        }
    }
}
