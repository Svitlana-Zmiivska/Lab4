package com.titapr.data;

import com.titapr.bean.LPR;
import com.titapr.data.mapper.LprRowMapper;
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
public class LprDAO {

    public static final String SQL_SELECT_FROM_lpr = "select * from lpr";
    public static final String SQL_SELECT_FROM_lpr_BY_IDD = "select * from lpr where lnum = ?";
    public static final String SQL_INSERT_INTO_lpr = "insert into lpr (lname, lrange) values (?, ?)";
    public static final String SQL_UPDATE_lpr = "update lpr set lname = ?, lrange =? where lnum = ?";
    public static final String SQL_DELETE_FROM_lpr = "delete from lpr where lnum = ? ";

    private JdbcTemplate mJdbcTemplate;

    public List<LPR> getAllLpr() {
        return mJdbcTemplate.query(SQL_SELECT_FROM_lpr, new LprRowMapper());
    }

    public LPR getLprById(long id) {
        List<LPR> lpr = mJdbcTemplate.query(SQL_SELECT_FROM_lpr_BY_IDD, new Object[]{id}, new LprRowMapper());
        return lpr.size() == 1 ? lpr.get(0) : null;
    }

    public LPR createLpr(final LPR LPR) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        mJdbcTemplate.update(new PreparedStatementCreator() {
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(SQL_INSERT_INTO_lpr, new String[]{"lnum"});
                ps.setString(1, LPR.getName());
                ps.setInt(2, LPR.getRange());
                return ps;
            }
        }, keyHolder);
        LPR.setNum(keyHolder.getKey().longValue());
        return LPR;
    }

    public void updateLpr(LPR LPR) {
        mJdbcTemplate.update(SQL_UPDATE_lpr,
                LPR.getName(),
                LPR.getRange(),
                LPR.getNum()
        );
    }

    public boolean deleteLpr(long num) {
        mJdbcTemplate.update(SQL_DELETE_FROM_lpr, num);
        return true;
    }


    @Autowired
    public void setDataSource(DataSource dataSource) {
        mJdbcTemplate = new JdbcTemplate(dataSource);
    }
}
