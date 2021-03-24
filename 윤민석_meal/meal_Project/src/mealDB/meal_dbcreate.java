package mealDB;

import java.sql.*;

public class meal_dbcreate {
	meal_dbcreate(){
		try {
			Connection con = Driver_connect.makeConnection("");
			Statement st = con.createStatement();
			String sql = "create database if not exists meal";
			
			st.execute(sql);
				
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}
		
	}

}
