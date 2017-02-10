package DBManager;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class DBManager 
{
	private static DataSource ds = null;

	private DBManager()
	{
		init();
	}// method end
	
	private void init()
	{
		// dbcp 를 통해  datasource 받아오기
		Context init = null;
		try 
		{
			init = new InitialContext();
			ds = (DataSource) init.lookup("java:comp/env/jdbc/member");
		} 
		catch (NamingException e){e.printStackTrace();}
	}
	
	public static Connection get_DB_Connection()
	{
		Connection conn = null;
		
		if(ds == null) // datasource 가 null 일 경우 새로 생성
		{
			new DBManager();
		}
		try 
		{
			// datasource를 통해서 db connection 받아오기
			conn = ds.getConnection();
		} 
		catch (SQLException e) 
		{ 
			e.printStackTrace();
		}
	
		return conn; // 커넥션 리턴
	}
}
