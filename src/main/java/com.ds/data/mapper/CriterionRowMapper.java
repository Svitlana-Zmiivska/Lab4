package com.ds.data.mapper;

import com.ds.bean.Criterion;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CriterionRowMapper implements RowMapper<Criterion> {

    @Override
    public Criterion mapRow(ResultSet pResultSet, int pRowNumber) throws SQLException {
        Criterion criterion = new Criterion();
        criterion.setNum(pResultSet.getLong("cnum"));
        criterion.setName(pResultSet.getString("cname"));
        criterion.setRange(pResultSet.getInt("crange"));
        criterion.setWeight(pResultSet.getInt("cweight"));
        criterion.setType(pResultSet.getString("ctype"));
        criterion.setOptimType(pResultSet.getString("optimType"));
        criterion.setEdIzmer(pResultSet.getString("edIzmer"));
        criterion.setScaleType(pResultSet.getString("scaleType"));
        return criterion;
    }


    private long num;
    private String name;
    private int range;
    private int weight;
    private String type;
    private String optimType;
    private String edIzmer;
    private String scaleType;
}
