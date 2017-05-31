package com.ds.data;

import com.ds.bean.Result;
import com.ds.data.mapper.ResultRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Repository
public class ResultDAO {

    public static final String SELECT_FROM_result = "select * from result";
    public static final String SELECT_FROM_result_BY_ID = "select * from result where anum = ?";
    public static final String SQL_INSERT_INTO_result = "insert into result (lnum, anum, range, aweight) values (?,?,?,?)";
    public static final String SQL_UPDATE_result = "update result set lnum=?, anum=?, range=?, aweight=?  where rnum = ?";
    public static final String SQL_DELETE_FROM_result = "delete from result where rnum = ? ";


    private JdbcTemplate mJdbcTemplate;

    public List<Result> getAllResult() {
        return mJdbcTemplate.query(SELECT_FROM_result, new ResultRowMapper());
    }

    public Result getResultByAltId(long id) {
        List<Result> result = mJdbcTemplate.query(SELECT_FROM_result_BY_ID, new Object[]{id}, new ResultRowMapper());
        return result.size() == 1 ? result.get(0) : null;
    }

    public Result createResult(final Result result) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        mJdbcTemplate.update(new PreparedStatementCreator() {
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(SQL_INSERT_INTO_result, new String[]{"rnum"});
                ps.setLong(1, result.getlNum());
                ps.setLong(2, result.getaNum());
                ps.setDouble(3, result.getRange());
                ps.setInt(4, result.getaWeight());
                return ps;
            }
        }, keyHolder);
        result.setNum(keyHolder.getKey().longValue());
        return result;
    }

    public void updateResult(Result result) {
        mJdbcTemplate.update(SQL_UPDATE_result,
                result.getlNum(),
                result.getaNum(),
                result.getRange(),
                result.getaWeight(),
                result.getNum()
        );
    }

    public boolean deleteResult(long num) {
        mJdbcTemplate.update(SQL_DELETE_FROM_result, num);
        return true;
    }


    @Autowired
    public void setDataSource(DataSource dataSource) {
        mJdbcTemplate = new JdbcTemplate(dataSource);
    }
}
