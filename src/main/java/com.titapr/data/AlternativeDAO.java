package com.titapr.data;

import com.titapr.bean.Alternative;
import com.titapr.data.mapper.AlternativeRowMapper;
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
public class AlternativeDAO {

    public static final String SELECT_FROM_ALTERNATIVE = "select * from alternative";
    public static final String SELECT_FROM_ALTERNATIVE_BY_ID = "select * from alternative where anum = ?";
    public static final String SQL_INSERT_INTO_ALTERNATIVE = "insert into alternative (aname) values (?)";
    public static final String SQL_UPDATE_ALTERNATIVE = "update alternative set aname = ? where anum = ?";
    public static final String SQL_DELETE_FROM_ALTERNATIVE = "delete from alternative where anum = ? ";

    @Autowired
    private VectorDAO vectorDAO;
    private JdbcTemplate mJdbcTemplate;

    public List<Alternative> getAllAlternative() {
        return mJdbcTemplate.query(SELECT_FROM_ALTERNATIVE, new AlternativeRowMapper());
    }

    public Alternative getAlternativeById(long id) {
        List<Alternative> alternative = mJdbcTemplate.query(SELECT_FROM_ALTERNATIVE_BY_ID, new Object[]{id}, new AlternativeRowMapper());
        return alternative.size() == 1 ? alternative.get(0) : null;
    }

    public Alternative createAlternative(final Alternative alternative) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        mJdbcTemplate.update(new PreparedStatementCreator() {
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(SQL_INSERT_INTO_ALTERNATIVE, new String[]{"anum"});
                ps.setString(1, alternative.getName());
                return ps;
            }
        }, keyHolder);
        alternative.setNum(keyHolder.getKey().longValue());
        return alternative;
    }

    public void updateAlternative(Alternative alternative) {
        mJdbcTemplate.update(SQL_UPDATE_ALTERNATIVE,
                alternative.getName(),
                alternative.getNum()
        );
    }

    public boolean deleteAlternative(long num) {
        vectorDAO.deleteVectorByAlternative(num);
        mJdbcTemplate.update(SQL_DELETE_FROM_ALTERNATIVE, num);
        return true;
    }


    @Autowired
    public void setDataSource(DataSource dataSource) {
        mJdbcTemplate = new JdbcTemplate(dataSource);
    }
}
