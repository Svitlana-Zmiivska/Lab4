package com.titapr.data.mapper;


import com.titapr.bean.Mark;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;


public class MarkRowMapper  implements RowMapper<Mark> {

    @Override
    public Mark mapRow(ResultSet pResultSet, int pRowNumber) throws SQLException {
        Mark mark = new Mark();
        mark.setNum(pResultSet.getLong("mnum"));
        mark.setcNum(pResultSet.getLong("cnum"));
        mark.setName(pResultSet.getString("MName"));
        mark.setRange(pResultSet.getInt("MRange"));
        mark.setNumMark(pResultSet.getDouble("NumMark"));
        mark.setNormMark(pResultSet.getInt("NormMark"));
        return mark;
    }
}