package com.leo.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import javax.transaction.UserTransaction;

public class DBUtils {
	
	public List<Integer> start() {
		List<Integer> items = new ArrayList<Integer>();
		try {
			Context cxt = new InitialContext();
			UserTransaction tx = (UserTransaction)cxt.lookup("javax.transaction.UserTransaction");
			DataSource ds = (DataSource)cxt.lookup("OracleDatasource");
			Connection conn = ds.getConnection();
			Statement stat = conn.createStatement();
			try {
				tx.begin();
				stat.executeUpdate(" insert into test(id,name)values(0,'gh')");
				tx.commit();
			} catch (Exception e) {
				tx.rollback();
				e.printStackTrace();
			}
			ResultSet rs = stat.executeQuery("select * from test");
			while (rs.next()) {
				System.out.println(rs.getInt("id"));
				items.add(rs.getInt("id"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return items;
	}

}
