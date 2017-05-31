package com.ds.data;

import com.ds.bean.Alternative;
import com.ds.bean.Mark;
import com.ds.bean.Vector;
import com.ds.data.mapper.MarkRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.SqlRowSetResultSetExtractor;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Repository
public class MarkDAO {

    public static final String SELECT_FROM_MARK_BY_ALT = "select * from mark where mnum in (select mnum from vector where anum = ?)";
    public static final String SELECT_FROM_MARK_BY_ID = "select * from mark where mnum = ?";
    public static final String SQL_SELECT_TOTAL_BY_CR = "select sum(nummark) as res from mark where cnum = ?";
    public static final String SQL_INSERT_INTO_MARK = "insert into mark (cnum, mname, mrange, nummark, normmark) values (?,?,?,?,?)";
    public static final String SQL_UPDATE_MARK = "update mark set cnum=?, mname=?, mrange=?, nummark=?, normmark=? where mnum = ?";
    public static final String SQL_DELETE_FROM_MARK = "delete from mark where mnum = ? ";
    public static final String SQL_DELETE_FROM_MARK_BY_CR = "delete from mark where cnum = ? ";


    @Autowired
    private VectorDAO vectorDAO;
    private JdbcTemplate mJdbcTemplate;

    public double getTotalCriteriaMark(long num) {
        SqlRowSet row = mJdbcTemplate.query(new PreparedStatementCreator() {
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(SQL_SELECT_TOTAL_BY_CR);
                ps.setLong(1, num);
                return ps;
            }
        },
        new SqlRowSetResultSetExtractor());
        row.first();
        return row.getBigDecimal("res").doubleValue();
    }

    public List<Mark> getMarksByAlternative(long anum) {
        return mJdbcTemplate.query(SELECT_FROM_MARK_BY_ALT, new Object[]{anum}, new MarkRowMapper());
    }

    public Mark getMarkById(long id) {
        List<Mark> mark = mJdbcTemplate.query(SELECT_FROM_MARK_BY_ID, new Object[]{id}, new MarkRowMapper());
        return mark.size() == 1 ? mark.get(0) : null;
    }

    public Mark createMark(final Mark mark, final Alternative alternative) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        mJdbcTemplate.update(new PreparedStatementCreator() {
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(SQL_INSERT_INTO_MARK, new String[]{"mnum"});
                ps.setLong(1, mark.getcNum());
                ps.setString(2, mark.getName());
                ps.setInt(3, mark.getRange());
                ps.setDouble(4, mark.getNumMark());
                ps.setInt(5, mark.getNormMark());
                return ps;
            }
        }, keyHolder);
        mark.setNum(keyHolder.getKey().longValue());
        Vector vector = new Vector();
        vector.setmNum(mark.getNum());
        vector.setaNum(alternative.getNum());
        vectorDAO.createVector(vector);
        return mark;
    }

    public void updateMark(Mark mark) {
        mJdbcTemplate.update(SQL_UPDATE_MARK,
                mark.getcNum(),
                mark.getName(),
                mark.getRange(),
                mark.getNumMark(),
                mark.getNormMark(),
                mark.getNum()
        );
    }

    public boolean deleteMark(long num) {
        mJdbcTemplate.update(SQL_DELETE_FROM_MARK, num);
        return true;
    }

    public void deleteMarksByCriterion(long num) {
        vectorDAO.deleteVectorsByCriterion(num);
        mJdbcTemplate.update(SQL_DELETE_FROM_MARK_BY_CR, num);
    }


    @Autowired
    public void setDataSource(DataSource dataSource) {
        mJdbcTemplate = new JdbcTemplate(dataSource);
    }
}
