package com.newlecture.jspprj.dao.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.newlecture.jspprj.dao.AnswerisDao;
import com.newlecture.jspprj.entity.Answeris;
import com.newlecture.jspprj.entity.AnswerisView;
import com.newlecture.jspweb.entity.Notice;
import com.newlecture.jspweb.entity.NoticeView;

public class JdbcAnswerisDao implements AnswerisDao {

	@Override
	public int insert(Answeris answeris) {
		
		String sql = "INSERT INTO ANSWERIS(ID,TITLE, LANGUAGE, PLATFORM, RUNTIME, ERROR_CODE, ERROR_MESSAGE,SITUATION, TRIED_TO_FIX, REASON, HOW_TO_FIX, WRITER_ID,ATTACHED_FILE) VALUES((SELECT NVL(MAX(TO_NUMBER(ID)),0)+1 ID FROM ANSWERIS),?,?,?,?,?,?,?,?,?,?,?,?)";
		int result = 0;
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");

			String url = "jdbc:oracle:thin:@211.238.142.251:1521:orcl";
			Connection con = DriverManager.getConnection(url, "c##sist", "dclass");
			PreparedStatement st = con.prepareStatement(sql);
			//Statement st = con.createStatement();
				    
			//st.setString(1, answeris.getId());
			st.setString(1, answeris.getTitle());
			st.setString(2, answeris.getLanguage());
			st.setString(3, answeris.getPlatform());
			st.setString(4, answeris.getRuntime());
			st.setString(5, answeris.getErrorCode());
			st.setString(6, answeris.getErrorMessage());
			st.setString(7, answeris.getSituation());
			st.setString(8, answeris.getTriedToFix());
			st.setString(9, answeris.getReason());
			st.setString(10, answeris.getHowToFix());
			st.setString(11, answeris.getWriterId());
			st.setString(12, answeris.getAttachedFile());

			result = st.executeUpdate(); //결과 반환

			st.close();
			con.close();

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public int update(Answeris answeris) {
		
		String sql = "UPDATE ANSWERIS SET TITLE = ?,LANGUAGE = ?, PLATFORM = ?, RUNTIME = ?, ERROR_CODE = ?, ERROR_MESSAGE = ?," 
				+ "SITUATION = ?, TRIED_TO_FIX = ?, REASON = ?, HOW_TO_FIX = ?, HIT = ?"
				+ "WHERE ID = ?";
		int result = 0;

		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");

			String url = "jdbc:oracle:thin:@211.238.142.251:1521:orcl";
			Connection con = DriverManager.getConnection(url, "c##sist", "dclass");
			PreparedStatement st = con.prepareStatement(sql);
			
			st.setString(1, answeris.getTitle());
			st.setString(2, answeris.getLanguage());
			st.setString(3, answeris.getPlatform());
			st.setString(4, answeris.getRuntime());
			st.setString(5, answeris.getErrorCode());
			st.setString(6, answeris.getErrorMessage());
			st.setString(7, answeris.getSituation());
			st.setString(8, answeris.getTriedToFix());
			st.setString(9, answeris.getReason());
			st.setString(10, answeris.getHowToFix());
			st.setInt(11, answeris.getHit());
			st.setString(12, answeris.getId());

			result = st.executeUpdate();
			//ResultSet rs = st.executeQuery();

			//rs.close();
			st.close();
			con.close();

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	}

	@Override
	public int delete(String id) {
		String sql = "DELETE ANSWERIS WHERE ID = ?";
		
		int result = 0;
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");

			String url = "jdbc:oracle:thin:@211.238.142.251:1521:orcl";
			Connection con = DriverManager.getConnection(url, "c##sist", "dclass");
			PreparedStatement st = con.prepareStatement(sql);
			st.setString(1, id);
			
			result = st.executeUpdate();

			//rs.close();
			st.close();
			con.close();

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	}

	@Override
	public List<AnswerisView> getList(int page) {
			
		int start = 1+(page-1)*15;
		int end = page*15;
		
		String sql = "SELECT * FROM ANSWERIS_VIEW WHERE NUM BETWEEN ? AND ?";

		List<AnswerisView> list = new ArrayList<>();
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");

		String url = "jdbc:oracle:thin:@211.238.142.251:1521:orcl";
		Connection con = DriverManager.getConnection(url, "c##sist", "dclass");
		PreparedStatement st = con.prepareStatement(sql);
		
		st.setInt(1, start);
		st.setInt(2, end);
	
		ResultSet rs = st.executeQuery();
		
		AnswerisView answeris = null;

		while (rs.next()) {
			
			AnswerisView answerisview = new AnswerisView(
					rs.getString("id"), 
					rs.getString("language"), 
					rs.getString("platform"), 
					rs.getString("runtime"), 
					rs.getString("title"), 
					rs.getString("error_Code"),
					rs.getString("error_Message"), 
					rs.getString("situation"), 
					rs.getString("tried_To_Fix"), 
					rs.getString("reason"), 
					rs.getString("writer_Id"), 
					rs.getString("how_To_Fix"),
					rs.getDate("reg_Date"), 
					rs.getInt("hit"),
					rs.getString("attached_file"),
					rs.getInt("comment_Count")
					);
			list.add(answerisview);
		}

		rs.close();
		st.close();
		con.close();
		
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public AnswerisView get(String id) {
			
		String sql = "SELECT * FROM ANSWERIS_VIEW WHERE ID = ?";

		AnswerisView answeris = null; // 초기값
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");

			String url = "jdbc:oracle:thin:@211.238.142.251:1521:orcl";
			Connection con = DriverManager.getConnection(url, "c##sist", "dclass");
			PreparedStatement st = con.prepareStatement(sql);
			st.setString(1, id);
			ResultSet rs = st.executeQuery();
			
			
			if(rs.next()) {
				answeris = new AnswerisView(
					
						rs.getString("id"), 
						rs.getString("language"), 
						rs.getString("platform"), 
						rs.getString("runtime"), 
						rs.getString("title"), 
						rs.getString("error_Code"),
						rs.getString("error_Message"), 
						rs.getString("situation"), 
						rs.getString("tried_To_Fix"), 
						rs.getString("reason"), 
						rs.getString("writer_Id"), 
						rs.getString("how_To_Fix"),
						rs.getDate("reg_Date"), 
						rs.getInt("hit"),
						rs.getString("attached_file"),
						rs.getInt("comment_Count")
					
				);
				//System.out.printf("id: %s, name: %s\n",id,name);
			}

			rs.close();
			st.close();
			con.close();

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return answeris;
	}

	@Override
	public int getCount() {
		String sql = "SELECT COUNT(ID) COUNT FROM ANSWERIS";

		int count = 0; // 초기값
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");

			String url = "jdbc:oracle:thin:@211.238.142.251:1521:orcl";
			Connection con = DriverManager.getConnection(url, "c##sist", "dclass");
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(sql);
				
			if(rs.next()) {
				count = rs.getInt("count");
				//System.out.printf("id: %s, name: %s\n",id,name);
			}

			rs.close();
			st.close();
			con.close();

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return count;
	}	

}
