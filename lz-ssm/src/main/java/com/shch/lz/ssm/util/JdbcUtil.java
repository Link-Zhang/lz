package com.shch.lz.ssm.util;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Link at 11:17 on 4/2/18.
 */
public class JdbcUtil {
    private Connection conn;
    private PreparedStatement pstmt;
    private ResultSet rs;

    public JdbcUtil(String driver, String url, String username, String password) {
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url, username, password);
            System.out.println("Database Connect Success!");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean updateByParams(String sql, List params) throws SQLException {
        int result = -1;
        pstmt = conn.prepareStatement(sql);
        int index = 1;
        if (null != params && !params.isEmpty()) {
            for (Object param : params) {
                pstmt.setObject(index++, param);
            }
        }
        result = pstmt.executeUpdate();
        return result > 0;
    }

    public List<Map> selectByParams(String sql, List params) throws SQLException {
        List<Map> result = new ArrayList<>();
        int index = 1;
        pstmt = conn.prepareStatement(sql);
        if (null != params && !params.isEmpty()) {
            for (Object param : params) {
                pstmt.setObject(index++, param);
            }
        }
        rs = pstmt.executeQuery();
        ResultSetMetaData resultSetMetaData = rs.getMetaData();
        int column = resultSetMetaData.getColumnCount();
        while (rs.next()) {
            Map map = new HashMap(column);
            for (int i = 0; i < column; i++) {
                String columnName = resultSetMetaData.getColumnName(i + 1);
                Object columnValue = rs.getObject(columnName);
                if (null == columnValue) {
                    columnValue = "";
                }
                map.put(columnName, columnValue);
            }
            result.add(map);
        }
        return result;
    }

    public void release() {
        try {
            if (null != rs) {
                rs.close();
            }
            if (null != pstmt) {
                pstmt.close();
            }
            if (null != conn) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Database Connection Release!");
    }
}
