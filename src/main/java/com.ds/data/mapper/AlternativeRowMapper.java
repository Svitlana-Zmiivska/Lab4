package com.ds.data.mapper;

import com.ds.bean.Alternative;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AlternativeRowMapper implements RowMapper<Alternative> {

    @Override
    public Alternative mapRow(ResultSet pResultSet, int pRowNumber) throws SQLException {
        Alternative alternative = new Alternative();
        alternative.setNum(pResultSet.getLong("anum"));
        alternative.setName(pResultSet.getString("aname"));
        return alternative;
    }
}
