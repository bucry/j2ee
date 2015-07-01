package com.oracle.mdb;

import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.sql.DataSource;

import com.oracle.mdb.remote.Student;
/**
 * Student实现类
 * @author Administrator
 *
 */
@Stateless(name="StudentBean")
public class StudentBean implements Student {

	//采用依赖注入数据源
	@Resource(name="Oracle")
	private DataSource ds;
	public void add(String name, String gender, int age) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try{
			//通过数据源获取数据库连接
			conn = ds.getConnection();
			//使用PreparedStatement执行SQL语句
			pstmt = conn.prepareStatement("select * from emp where id=?");
			pstmt.setString(1, name);
			pstmt.executeUpdate();
		}finally{
			pstmt.close();
			conn.close();
		}
	}

}
