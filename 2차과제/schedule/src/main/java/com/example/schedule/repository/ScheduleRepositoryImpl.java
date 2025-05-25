package com.example.schedule.repository;

import com.example.schedule.dto.ScheduleResponseDto;
import com.example.schedule.entity.Schedule;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ScheduleRepositoryImpl implements ScheduleRepository {

    private final JdbcTemplate jdbcTemplate;

    public ScheduleRepositoryImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Long saveSchedule(Schedule schedule) {

        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("schedule")
                .usingGeneratedKeyColumns("id")
                .usingColumns("todo", "author", "password");;

        Map<String, Object> params = new HashMap<>();
        params.put("todo", schedule.getTodo());
        params.put("author", schedule.getAuthor());
        params.put("password", schedule.getPassword());

        // 저장 후 생성된 key값 Number 타입으로 반환하는 메서드
        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(params));

        // id 반환
        return key.longValue();
    }

    @Override
    public List<Schedule> findSchedules(String author, LocalDate updatedAt) {

        StringBuilder sql = new StringBuilder("select * from schedule where 1=1 ");
        List<Object> params = new ArrayList<>();

        // 작성자명 조건 포함
        if (author != null) {
            sql.append(" and author = ? ");
            params.add(author);
        }

        // 수정일 조건 포함
        if (updatedAt != null) {
            sql.append(" and DATE(updated_at) = ? ");   // YYYY-MM-DD
            params.add(updatedAt);
        }

        // 수정일 기준 내림차순 정렬
        sql.append("order by updated_at desc ");

        return jdbcTemplate.query(sql.toString(), scheduleRowMapper(), params.toArray());
    }

    @Override
    public Schedule findScheduleById(Long id) {

        List<Schedule> result = jdbcTemplate.query("select * from schedule where id = ?", scheduleRowMapper(), id);

        return result.stream().findAny().orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exists id = " + id));
    }

    @Override
    public void updateSchedule(Long id, String todo, String author) {

        StringBuilder sql = new StringBuilder("update schedule set ");
        List<Object> params = new ArrayList<>();

        boolean needsComma = false;

        // 할일 수정
        if (todo != null) {
            sql.append("todo = ?");
            params.add(todo);
            needsComma = true;
        }

        // 작성자명 수정
        if (author != null) {
            if (needsComma) {
                sql.append(", ");
            }
            sql.append("author = ?");
            params.add(author);
        }

        sql.append(" where id = ? ");
        params.add(id);

        jdbcTemplate.update(sql.toString(), params.toArray());
    }

    @Override
    public void deleteSchedule(Long id) {
        jdbcTemplate.update("delete from schedule where id = ?", id);
    }

    private RowMapper<Schedule> scheduleRowMapper() {

        return new RowMapper<Schedule>() {

            @Override
            public Schedule mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new Schedule(
                        rs.getLong("id"),
                        rs.getString("todo"),
                        rs.getString("author"),
                        rs.getString("password"),
                        rs.getTimestamp("created_at").toLocalDateTime(),
                        rs.getTimestamp("updated_at").toLocalDateTime()
                );
            }
        };
    }
}
