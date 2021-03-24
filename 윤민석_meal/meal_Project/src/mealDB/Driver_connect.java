package mealDB;
import java.sql.*;

public class Driver_connect {
	
 public static Connection makeConnection(String text) {
	 String url = "jdbc:mysql://localhost/"+text;
	 String id  = "root";
	 String pw = "1234";
	 Connection con = null;
	 
	 try {
		 Class.forName("com.mysql.jdbc.Driver");
		 System.out.println("드라이브 적재 성공");
		 con = DriverManager.getConnection(url, id, pw);
		 System.out.println("데이터 베이스 연결 성공");
		 
	 }catch(ClassNotFoundException e) {
		 System.out.println(e.getMessage());
	 }catch(SQLException e) {
		 System.out.println(e.getMessage());
	 }
	 return con;
 }
}
