package com.ds.data;

import com.ds.bean.Criterion;
import com.ds.data.mapper.CriterionRowMapper;
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
public class CriterionDAO {

    public static final String SELECT_FROM_CRITERION = "select * from criterion";
    public static final String SELECT_FROM_CRITERION_BY_ID = "select * from criterion where cnum = ?";
    public static final String SQL_INSERT_INTO_CRITERION = "insert into criterion (cname, crange, cweight, ctype, optimtype, edizmer, scaletype) values (?, ?, ?, ?, ?, ?, ?)";
    public static final String SQL_UPDATE_CRITERION = "update criterion set cname=?, crange=?, cweight=?, ctype=?, optimtype=?, edizmer=?, scaletype=? where cnum = ?";
    public static final String SQL_DELETE_FROM_CRITERION = "delete from criterion where cnum = ? ";

    @Autowired
    private MarkDAO markDAO;
    private JdbcTemplate mJdbcTemplate;

    public List<Criterion> getAllCriterion() {
        return mJdbcTemplate.query(SELECT_FROM_CRITERION, new CriterionRowMapper());
    }

    public Criterion getCriterionById(long id) {
        List<Criterion> criterion = mJdbcTemplate.query(SELECT_FROM_CRITERION_BY_ID, new Object[]{id}, new CriterionRowMapper());
        return criterion.size() == 1 ? criterion.get(0) : null;
    }

    public Criterion createCriterion(final Criterion criterion) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        mJdbcTemplate.update(new PreparedStatementCreator() {
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(SQL_INSERT_INTO_CRITERION, new String[]{"cnum"});
                ps.setString(1, criterion.getName());
                ps.setInt(2, criterion.getRange());
                ps.setInt(3, criterion.getWeight());
                ps.setString(4, criterion.getType());
                ps.setString(5, criterion.getOptimType());
                ps.setString(6, criterion.getEdIzmer());
                ps.setString(7, criterion.getScaleType());
                return ps;
            }
        }, keyHolder);
        criterion.setNum(keyHolder.getKey().longValue());
        return criterion;
    }

    public void updateCriterion(Criterion criterion) {
        mJdbcTemplate.update(SQL_UPDATE_CRITERION,
                criterion.getName(),
                criterion.getRange(),
                criterion.getWeight(),
                criterion.getType(),
                criterion.getOptimType(),
                criterion.getEdIzmer(),
                criterion.getScaleType(),
                criterion.getNum()
        );
    }

    public boolean deleteCriterion(long num) {
        markDAO.deleteMarksByCriterion(num);
        mJdbcTemplate.update(SQL_DELETE_FROM_CRITERION, num);
        return true;
    }


    @Autowired
    public void setDataSource(DataSource dataSource) {
        mJdbcTemplate = new JdbcTemplate(dataSource);
    }
}
