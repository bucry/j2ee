package com.framework.core.database;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author bfc
 */
//public abstract class RowMapper<T> implements ParameterizedRowMapper<T> {
public abstract class RowMapper<T> implements org.springframework.jdbc.core.RowMapper<T> {
    private Map<String, Integer> columnIndexes;

    // JDBC doesn't support map to not-existed column,
    // we cache column indexes to make row mapper can be reused by multiple SQL

    protected int findColumn(ResultSet resultSet, String columnName) throws SQLException {
        if (columnIndexes == null) {
            buildIndexes(resultSet);
        }
        Integer index = columnIndexes.get(columnName);
        if (index == null) return -1;
        return index;
    }

    private void buildIndexes(ResultSet resultSet) throws SQLException {
        columnIndexes = new HashMap<String, Integer>();
        ResultSetMetaData meta = resultSet.getMetaData();
        int count = meta.getColumnCount();
        for (int i = 1; i < count + 1; i++) {
            String column = meta.getColumnName(i);
            columnIndexes.put(column.toLowerCase(), i);
        }
    }

    protected String getString(ResultSet resultSet, String column) throws SQLException {
        return getString(resultSet, column, null);
    }

    protected String getString(ResultSet resultSet, String column, String defaultValue) throws SQLException {
        int columnIndex = findColumn(resultSet, column);
        if (columnIndex > 0) return resultSet.getString(columnIndex);
        return defaultValue;
    }

    protected Date getDate(ResultSet resultSet, String column) throws SQLException {
        return getDate(resultSet, column, null);
    }

    protected Date getDate(ResultSet resultSet, String column, Date defaultValue) throws SQLException {
        int columnIndex = findColumn(resultSet, column);
        if (columnIndex > 0) return resultSet.getTimestamp(columnIndex);
        return defaultValue;
    }

    protected int getInt(ResultSet resultSet, String column) throws SQLException {
        return getInt(resultSet, column, 0);
    }

    protected int getInt(ResultSet resultSet, String column, int defaultValue) throws SQLException {
        int columnIndex = findColumn(resultSet, column);
        if (columnIndex > 0) return resultSet.getInt(columnIndex);
        return defaultValue;
    }

    protected double getDouble(ResultSet resultSet, String column) throws SQLException {
        return getDouble(resultSet, column, 0);
    }

    protected double getDouble(ResultSet resultSet, String column, double defaultValue) throws SQLException {
        int columnIndex = findColumn(resultSet, column);
        if (columnIndex > 0) return resultSet.getDouble(columnIndex);
        return defaultValue;
    }

    public abstract T mapRow(ResultSet resultSet, int rowNum) throws SQLException;
}
