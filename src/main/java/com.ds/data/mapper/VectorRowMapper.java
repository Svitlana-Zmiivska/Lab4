package com.ds.data.mapper;

import com.ds.bean.Vector;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;


public class VectorRowMapper  implements RowMapper<Vector> {

    @Override
    public Vector mapRow(ResultSet pResultSet, int pRowNumber) throws SQLException {
        Vector vector = new Vector();
        vector.setNum(pResultSet.getLong("vnum"));
        vector.setaNum(pResultSet.getLong("anum"));
        vector.setmNum(pResultSet.getLong("mnum"));
        return vector;
    }
}
