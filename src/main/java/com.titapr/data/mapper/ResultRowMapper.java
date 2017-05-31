package com.ds.data.mapper;

import com.ds.bean.Result;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;


public class ResultRowMapper implements RowMapper<Result> {

    @Override
    public Result mapRow(ResultSet pResultSet, int pRowNumber) throws SQLException {
        Result result = new Result();
        result.setNum(pResultSet.getLong("rnum"));
        result.setlNum(pResultSet.getLong("lnum"));
        result.setaNum(pResultSet.getLong("anum"));
        result.setRange(pResultSet.getDouble("range"));
        result.setaWeight(pResultSet.getInt("aweight"));
        return result;
    }
}