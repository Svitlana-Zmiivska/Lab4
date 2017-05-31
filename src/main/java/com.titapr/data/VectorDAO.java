package com.ds.data;

import com.ds.bean.Vector;
import com.ds.data.mapper.VectorRowMapper;
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
public class VectorDAO {

    public static final String SELECT_FROM_vector = "select * from vector";
    public static final String SELECT_FROM_vector_BY_ID = "select * from vector where v`num = ?";
    public static final String SQL_INSERT_INTO_vector = "insert into vector (anum, mnum) values (?,?)";
    public static final String SQL_UPDATE_vector = "update vector set anum=?, mnum=? where vnum = ?";
    public static final String SQL_DELETE_FROM_vector = "delete from vector where anum = ? ";
    public static final String SQL_DELETE_FROM_vector_by_cr = "delete from vector where mnum in (select mnum from mark where cnum = ?)";

    private JdbcTemplate mJdbcTemplate;

    public List<Vector> getAllVector() {
        return mJdbcTemplate.query(SELECT_FROM_vector, new VectorRowMapper());
    }

    public Vector getVectorById(long id) {
        List<Vector> vector = mJdbcTemplate.query(SELECT_FROM_vector_BY_ID, new Object[]{id}, new VectorRowMapper());
        return vector.size() == 1 ? vector.get(0) : null;
    }

    public Vector createVector(final Vector vector) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        mJdbcTemplate.update(new PreparedStatementCreator() {
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(SQL_INSERT_INTO_vector, new String[]{"vnum"});
                ps.setLong(1, vector.getaNum());
                ps.setLong(2, vector.getmNum());
                return ps;
            }
        }, keyHolder);
        vector.setNum(keyHolder.getKey().longValue());
        return vector;
    }

    public void updateVector(Vector vector) {
        mJdbcTemplate.update(SQL_UPDATE_vector,
                vector.getaNum(),
                vector.getmNum(),
                vector.getNum()
        );
    }

    public boolean deleteVectorByAlternative(long num) {
        mJdbcTemplate.update(SQL_DELETE_FROM_vector, num);
        mJdbcTemplate.update("delete from mark where mnum not in (select mnum from vector)");
        return true;
    }

    public boolean deleteVectorsByCriterion(long num) {
        mJdbcTemplate.update(SQL_DELETE_FROM_vector_by_cr, num);
        return true;
    }


    @Autowired
    public void setDataSource(DataSource dataSource) {
        mJdbcTemplate = new JdbcTemplate(dataSource);
    }
}
