package com.ds.data.mapper;

import com.ds.bean.LPR;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;


public class LprRowMapper implements RowMapper<LPR> {

    @Override
    public LPR mapRow(ResultSet pResultSet, int pRowNumber) throws SQLException {
        LPR user = new LPR();
        user.setNum(pResultSet.getLong("lnum"));
        user.setName(pResultSet.getString("lname"));
        user.setRange(pResultSet.getInt("lrange"));
        return user;
    }
}